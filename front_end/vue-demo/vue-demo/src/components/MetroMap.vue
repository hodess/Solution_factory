<script>
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { chemin_json } from '@/config';
import Paths from "@/components/paths.vue";
import eventBus from '../../eventBus.js';
import axios from "axios";
import {onMounted , ref} from "vue";

let gares_map = []

export default {
  created() {
    this.eventBusHandler = (numChemin) => {
      console.log(`Événement cheminClique reçu avec numéro : ${numChemin}`);
      switch (numChemin) {
        case 1:
          this.traceChemin(1)
          break;
        case 2:
          this.traceChemin(2)
          break;
        case 3:
          this.traceChemin(3)
          break;
        default:
          console.error('Numéro de chemin invalide :', numChemin);
          break;
      }
    };

    eventBus.on('cheminClique', this.eventBusHandler);
  },
  name: 'LeafletMap',
  components: { Paths },
  data() {
    return {
      markersLayer: null,
      linesLayerGroup: null,
      metroMarkers: [],
      triangleIcon: null,
      currentLine: null,
      selectedLine: null,
      lineImages: {
        'ligne_1': 'src/components/lines/metro-1.png',
        'ligne_2': 'src/components/lines/metro-2.png',
        'ligne_3': 'src/components/lines/metro-3.png',
        'ligne_3B': 'src/components/lines/metro-3-bis.png',
        'ligne_4': 'src/components/lines/metro-4.png',
        'ligne_5': 'src/components/lines/metro-5.png',
        'ligne_6': 'src/components/lines/metro-6.png',
        'ligne_7': 'src/components/lines/metro-7.png',
        'ligne_7B': 'src/components/lines/metro-7-bis.png',
        'ligne_8': 'src/components/lines/metro-8.png',
        'ligne_9': 'src/components/lines/metro-9.png',
        'ligne_10': 'src/components/lines/metro-10.png',
        'ligne_11': 'src/components/lines/metro-11.png',
        'ligne_12': 'src/components/lines/metro-12.png',
        'ligne_13': 'src/components/lines/metro-13.png',
        'ligne_14': 'src/components/lines/metro-14.png',
        'ligne_A': 'src/components/lines/rer-a.png',
        'ligne_B': 'src/components/lines/rer-b.png',
        'ligne_C': 'src/components/lines/rer-c.png',
        'ligne_D': 'src/components/lines/rer-d.png',
        'ligne_E': 'src/components/lines/rer-e.png',
      },
      metroColors: {
        '1': '#FFCD00', // Jaune
        '2': '#003CA6', // Bleu
        '3': '#837902', // Vert
        '3B': '#6EC4E8', // Bleu clair
        '4': '#CF009E', // Fuchsia
        '5': '#FF7E2E', // Orange
        '6': '#6ECA97', // Vert clair
        '7': '#FA9ABA', // Rose
        '7B': '#6ECA97', // Vert clair
        '8': '#E19BDF', // Lilas
        '9': '#B4A630', // Olive
        '10': '#C9910D', // Jaune foncé
        '11': '#704B1C', // Marron
        '12': '#007852', // Vert émeraude
        '13': '#6EC4E8', // Bleu clair
        '14': '#62259D' // Violet
      }
    };
  },
  computed: {
    lineColors() {
      return {
        ligne_1: '#FFCE00',     // Jaune
        ligne_2: '#0064B0',     // Bleu foncé
        ligne_3: '#9F9825',     // Vert olive
        ligne_3B: '#98D4E2',  // Bleu ciel
        ligne_4: '#C04191',     // Fuchsia
        ligne_5: '#F28E42',     // Orange
        ligne_6: '#83C491',     // Vert clair
        ligne_7: '#F3A4BA',     // Rose
        ligne_7B: '#83C491',  // Jaune vert
        ligne_8: '#CEADD2',     // Lilas
        ligne_9: '#D5C900',     // Vert pomme
        ligne_10: '#E3B32A',    // Orange clair
        ligne_11: '#8D5E2A',    // Marron
        ligne_12: '#00814F',    // Vert bouteille
        ligne_13: '#98D4E2',    // Bleu ciel (identique à la 3bis)
        ligne_14: '#662483',     // Violet
        ligne_A: '#F7403A',     // Rouge
        ligne_B: '#4B92DB',    // Rouge
        ligne_C: '#F3D311',     // Rouge
        ligne_D: '#3F9C35',     // Rouge
        ligne_E: '#DE81D3',     // Rouge
      };
    }
  },
  async mounted() {
    this.initMap();
    const Mapordemarre = localStorage.getItem('currentDepart');
    if (!Mapordemarre){
      this.ShowAllLineWithColor();
    }
  },
  methods: {
    // initialisation de la map
    initMap() {
      this.map = L.map('map').setView([48.8566, 2.3522], 13); // Coordonnées pour centrer la carte sur Paris


      L.tileLayer('https://api.maptiler.com/maps/positron/{z}/{x}/{y}.png?key=kGzEOK5vmVP8dEjh59c5', {
        attribution: '&copy; <a href="https://www.maptiler.com/copyright/">MapTiler</a>',
        tileSize: 512,
        zoomOffset: -1,
        minZoom: 0,
        maxZoom: 18,
        accessToken: 'kGzEOK5vmVP8dEjh59c5'
      }).addTo(this.map);

      // Ajouter un nouveau contrôle de zoom en bas à gauche
      L.control.zoom({
        position: 'bottomright'
      }).addTo(this.map);

      this.linesLayerGroup = L.layerGroup().addTo(this.map);
      this.markersLayer = L.layerGroup().addTo(this.map);

      // this.addMarkersAsCircles(this.chemin_json);

    },

    // les fonctions générales pour gérer des choses sur la map
    zoomToBounds(bounds) {
      if (bounds && bounds.isValid && bounds.isValid()) {
        this.map.fitBounds(bounds, { paddingTopLeft: [window.innerWidth * 0.2, 0] });
      }
    },

    // récupérer les données
    async fetchAndLogResult() {
      try {
        const response = await axios.get('http://localhost:8081/garesMap');
        const metroData = response.data;

        // Convertir les coordonnées en tableaux JavaScript
        for (const key in metroData) {
          metroData[key].forEach(station => {
            station.coord = JSON.parse(station.coord.replace(/'/g, '"'));
          });
        }

        console.log('Data fetched from API:', metroData);
        return metroData;
      } catch (error) {
        console.error('Error fetching data:', error);
        return {};
      }
    },

    // carte avec tout les points
    async addMarkersAsCircles(metroData) {
      try {
        const response = await axios.get('http://localhost:8081/garesMap');
        const metroData = response.data;

        for (const key in metroData) {
          metroData[key].forEach(station => {
            station.coord = JSON.parse(station.coord.replace(/'/g, '"'));
          });
        }

        this.linesLayerGroup.clearLayers();
        this.markersLayer.clearLayers();

        const lines = {};

        for (const key in metroData) {
          lines[key] = [];

          metroData[key].forEach(station => {
            const [longitude, latitude] = station.coord;

            lines[key].push([latitude, longitude]);
            const key_ligne=key.split("/")
            console.log("key"+key_ligne[0])
            const lineColor = this.metroColors[key_ligne[0].replace(/\s/g, '')];

            L.circle([latitude, longitude], {
              color: lineColor,
              fillColor: lineColor,
              fillOpacity: 1,
              radius: 10
            }).addTo(this.markersLayer).bindPopup(station.name); // Changez this.map par this.markersLayer
          });
        }

        console.log('Stations ajoutées sur la carte:', metroData);
      } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
      }
    },

    async showAllLines() {
      try {
        this.currentLine = null; // Réinitialise la ligne actuelle
        // Effacez les layersGroup pour éviter les doublons
        this.linesLayerGroup.clearLayers();
        this.markersLayer.clearLayers(); // Efface tous les marqueurs affichés

        const garesMap = await this.fetchAndLogResult();
        this.addMarkersAsCircles(garesMap);
      } catch (error) {
        console.error('Erreur lors de l\'affichage de toutes les lignes:', error);
      }
    },

    async ShowAllLineWithColor() {
      try {
        const response = await axios.get('http://localhost:8081/garesMap');
        const metroData = response.data;

        for (const key in metroData) {
          metroData[key].forEach(station => {
            station.coord = JSON.parse(station.coord.replace(/'/g, '"'));
          });
        }

        this.linesLayerGroup.clearLayers();
        this.markersLayer.clearLayers();

        const lines = {};

        for (const key in metroData) {
          lines[key] = [];
          const key_ligne=key.split("/")
          console.log(key_ligne[0])

          metroData[key].forEach(station => {
            const [longitude, latitude] = station.coord;

            lines[key].push([latitude, longitude]);

            const lineColor = this.metroColors[key_ligne[0].replace(/\s/g, '')];

            L.circle([latitude, longitude], {
              color: lineColor,
              fillColor: lineColor,
              fillOpacity: 1,
              radius: 10
            }).addTo(this.markersLayer).bindPopup(station.name); // Changez this.map par this.markersLayer
          });

          const lineColor = this.metroColors[key_ligne[0].replace(/\s/g, '')];
          L.polyline(lines[key], { color: lineColor }).addTo(this.linesLayerGroup);
        }

        console.log('Stations ajoutées sur la carte:', metroData);
      } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
      }
    },

    async toggleLinesWithColor() {
      // this.linesLayerGroup.clearLayers();
      // this.markersLayer.clearLayers();

      // enlever les lignes
      if (this.linesDisplayed) {
        await this.showAllLines();
        this.linesDisplayed = false;
      } else {
        //afficher les lignes
        await this.ShowAllLineWithColor();
        this.linesDisplayed = true;
      }
    },

    // Dropdown button
    selectLine(line) {
      if (this.currentLine === line) {
        this.showAllLines(); // Si la ligne est déjà sélectionnée, afficher toutes les stations
      } else {
        this.toggleLine(line); // Sinon, afficher la ligne sélectionnée
      }
    },

    async toggleLine(line) {
      /*this.linesLayerGroup.clearLayers();
      this.markersLayer.clearLayers();
      this.metroMarkers = [];*/

      const garesMap = await this.fetchAndLogResult();
      await this.addMarkersAsCircles(garesMap);

      const selectedLine = ` ${line.replace('ligne_', '')}`;

      if (this.currentLine === selectedLine) {
        this.showAllLines();
        this.currentLine = null;
      } else {
        this.currentLine = selectedLine;
        this.fetchAndDisplayLine(selectedLine);
      }
    },

    async fetchAndDisplayLine(line_given) {
      try {
        const response = await axios.get('http://localhost:8081/garesMap');
        const metroData = response.data;
        let count_ligne=[]
        for (let key in metroData){
          const key_ligne=key.split("/")
          if (key_ligne[0]==line_given){
            count_ligne.push(key);
          }
        }
        console.log(count_ligne)

        // this.linesLayerGroup.clearLayers();
        // this.markersLayer.clearLayers();
        for (let line of count_ligne){
          console.log(line)
          if (metroData[line]) {
            const key_line=line.split("/")
            const bounds = this.addMetroLines(metroData[line], key_line[0]);
            this.addMarkersWithColor(metroData[line], key_line[0]);
            this.zoomToBounds(bounds);
          } else {
            console.error(`No data found for line: ${line}`);
          }
        }Ò
        } catch (error) {
          console.error('Error fetching and displaying line:', error);
        }
    },


    addMetroLines(stations, line) {
      console.log(line.trim())
      const lineColor = this.metroColors[line.trim()] || 'black'; // Supprimez les espaces pour obtenir la couleur correcte
      let bounds = [];

      const polyline = L.polyline(
          stations.map(station => {
            const { coord } = station;

            // Debug: Afficher le type et la valeur de coord
            console.log(`coord type: ${typeof coord}`, coord);

            let longitude, latitude;
            if (Array.isArray(coord)) {
              [longitude, latitude] = coord;
            } else {
              [longitude, latitude] = JSON.parse(coord.replace(/'/g, '"'));
            }

            return [latitude, longitude];
          }),
          { color: lineColor }
      );

      this.linesLayerGroup.addLayer(polyline);
      bounds = polyline.getBounds();

      return bounds;
    },

    addMarkersWithColor(stations, line) {
      console.log(line.trim())
      const lineColor = this.metroColors[line.trim()] || 'black'; // Supprimez les espaces pour obtenir la couleur correcte

      stations.forEach(station => {
        const { coord } = station;

        // Vérifiez si coord est déjà un tableau
        let longitude, latitude;
        if (Array.isArray(coord)) {
          [longitude, latitude] = coord;
        } else {
          // Supposons que coord soit une chaîne de caractères, essayez de la parser
          [longitude, latitude] = JSON.parse(coord.replace(/'/g, '"'));
        }

        // Créer un cercle avec la couleur de la ligne
        const circle = L.circle([latitude, longitude], {
          color: lineColor,
          fillColor: lineColor,
          fillOpacity: 0.8,
          radius: 30
        }).addTo(this.markersLayer);

        // Ajouter un popup avec le nom de la station
        circle.bindPopup(station.name);
      });
    },

    clearMap() {
      // Effacer toutes les couches de lignes
      this.linesLayerGroup.clearLayers();

      // Effacer tous les marqueurs
      this.markersLayer.clearLayers();

      // Réinitialiser la liste des marqueurs de métro
      this.metroMarkers = [];
    },

    // Trace le trajet de l'utilisateur
    traceChemin(cheminIndex) {
      // Effacer toutes les couches existantes de la carte
      this.clearMap();

      let chemin_affiche = chemin_json.value.chemins[cheminIndex - 1];

      if (!chemin_affiche) {
        return;
      }

      let bounds = new L.LatLngBounds(); // Initialise les limites pour calculer le zoom

      for (let key in chemin_affiche) {
        if (chemin_affiche.hasOwnProperty(key) && chemin_affiche[key].Gare) {
          let gares = chemin_affiche[key].Gare;
          let previousCoord = null;
          let previousLine = null;

          gares.forEach(gare => {
            if (!gare.coord || gare.coord.length !== 2) {
              console.warn("Coordonnées invalides pour la gare : ", gare);
              return;
            }

            let coord = gare.coord.slice(); // Copie de coord pour éviter de modifier l'original
            const temp = coord[0];
            coord[0] = coord[1];
            coord[1] = temp;

            const lineKey = "ligne_" + key.trim();
            console.log("ligne key : " + lineKey);
            const color = this.lineColors[lineKey] || 'black'; // Couleur de la ligne de métro

            // Création du marqueur de cercle pour la station
            const marker = L.circleMarker([coord[0], coord[1]], {
              radius: 6,
              fillColor: color,
              color: color,
              weight: 1,
              opacity: 1,
              fillOpacity: 1
            }).bindPopup(gare.name); // Popup avec le nom de la station

            console.log("marker");
            console.log(marker);

            this.markersLayer.addLayer(marker);
            console.log("previous cord " + previousCoord);

            // Relier les stations de la même ligne
            if (previousCoord && previousLine === lineKey) {
              const polyline = L.polyline([previousCoord, coord], { color: color }).addTo(this.map);
              this.linesLayerGroup.addLayer(polyline);
            }

            previousCoord = [coord[0], coord[1]];
            previousLine = lineKey;

            // Étendre les limites de la carte avec les nouvelles coordonnées
            bounds.extend([coord[0], coord[1]]);
          });
        }
      }

      // Ajuster le zoom pour afficher toutes les lignes avec un padding à gauche de 20 rem
      if (bounds.isValid()) {
        this.map.fitBounds(bounds, { padding: [0, window.innerWidth * 0.1] });
      }
    },


    },
  watch: {
    '$parent': {
      handler: function (numChemin) {
        console.log(`Événement cheminClique reçu avec numéro : ${numChemin}`);
        this.traceChemin(numChemin);
      },
      deep: true
    }
  },
};

</script>

<template>
  <div>
    <div id="map"></div>
    <div>
      <div class="dropdown">
        <button class="dropbtn"></button>
        <div class="dropdown-content">
          <button class="all-lines" @click="toggleLinesWithColor"></button>
          <div v-for="(imagePath, line) in lineImages" :key="line" @click="selectLine(line)">
            <img :src="imagePath" alt="Icone ligne de métro" style="width: 30px; height: 30px;">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<style>
select {
  padding: 0.5rem;
  font-size: 1rem;
}

#map {
  height: 100vh;
  width: 100vw;
  flex-grow: 1;
}
.dropdown {
  position: fixed;
  top: 93vh;
  left: 50vw;
  z-index: 100000;
}

.dropbtn {

  width: 3rem;
  height: 3rem;
  background-image: url("../components/lines/logo-metro.png");
  background-position: center;
  background-size: cover;
  background-repeat: no-repeat;
  border-width: 0;
  background-color: transparent;
  border-radius: 15px;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: fit-content;
  box-shadow: 0 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 2;
  top: -20rem;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 15px;
  max-height: 20rem; /* Ajoutez une hauteur maximale */
  overflow-y: auto; /* Ajoutez défilement vertical */
}


.dropdown-content div {
  display: flex;
  align-items: center;
  padding: 8px 12px;
  cursor: pointer;
}

.dropdown-content div:hover {
  background-color: #ddd;
}

.dropdown:hover .dropdown-content {
  display: block;
}

.dropdown:hover .dropbtn {
  background-color: #f1f1f1;
}

.all-lines {
  position: relative;
  width: 3.3rem;
  height: 3.3rem;
  background-image: url("icons/map.png");
  background-size: cover;
  background-position: center;
  border-radius: 20px;
  background-color: white;
  border-style: solid;
  box-shadow: white;
}

</style>
