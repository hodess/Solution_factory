<script>
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { chemin_json } from '@/config';
import Paths from "@/components/paths.vue";
import eventBus from '../../eventBus.js';


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
  setup() {
    console.log("chemin au setup : ")
    console.log(chemin_json.value); // Affiche 'Chemin depuis Component1'
    console.log("je recupere bien le json depuis paths")
  },
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
  mounted() {
    this.initMap();
    // this.showAllLines(); // Afficher tous les ronds noirs par défaut
  },
  methods: {
    initMap() {
      this.map = L.map('map', {
        center: [48.8566, 2.3522],
        zoom: 13,
        zoomControl: false  // Désactiver le contrôle de zoom par défaut
      });

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
    // A changer pour mettre les stations au lancement de la map. Actuellement on a pas importé ces données
    /*addMarkersAsCircles(metroData) {
      for (const line in metroData) {
        const stations = metroData[line].stations;
        stations.forEach(station => {
          const {latitude, longitude, nom} = station.coordonnees;
          const marker = L.circleMarker([latitude, longitude], {
            radius: 5,
            fillColor: 'Grey',
            color: 'grey',
            weight: 1,
            opacity: 1,
            fillOpacity: 1
          }).bindPopup(nom); // Bind popup with station name

          marker.on('click', () => {  // Add click event to open popup
            marker.openPopup();
          });

          this.markersLayer.addLayer(marker);
          this.metroMarkers.push({line, marker});
        });
      }
    },*/
    /*
    addMetroLines(metroData, line) {
      const lineColor = this.lineColors[line] || 'black';
      let bounds = [];

      for (const lineKey in metroData) {
        const stations = metroData[lineKey].stations;
        const polyline = L.polyline(stations.map(station => [station.coordonnees.latitude, station.coordonnees.longitude]), {color: lineColor});

        if (lineKey === line) {
          this.linesLayerGroup.addLayer(polyline);
          bounds = polyline.getBounds();
        }
      }

      return bounds;
    },*/
    /*
    toggleLine(line) {
      this.currentLine = line;
      this.linesLayerGroup.clearLayers();
      this.markersLayer.clearLayers();

      if (this.currentLine) {
        const bounds = this.addMetroLines(this.chemin, this.currentLine);
        this.addMarkersWithColor(this.chemin, this.currentLine);

        this.zoomToBounds(bounds);
      }
    },*/
    /*
    addMarkersWithColor(metroData, line) {
      const lineColor = this.lineColors[line] || 'black';

      for (const lineKey in metroData) {
        const stations = metroData[lineKey].stations;
        stations.forEach(station => {
          const {latitude, longitude, nom} = station.coordonnees;
          const marker = L.circleMarker([latitude, longitude], {
            radius: 6,
            fillColor: lineColor,
            color: lineColor,
            weight: 1,
            opacity: 1,
            fillOpacity: 1
          }).bindPopup(nom);

          if (lineKey === line) {
            this.markersLayer.addLayer(marker);
            this.metroMarkers.push({line, marker});
          }
        });
      }
    },*/
    /*showAllLines() {
      this.currentLine = null; // Réinitialise la ligne actuelle
      this.linesLayerGroup.clearLayers(); // Efface toutes les lignes affichées
      this.markersLayer.clearLayers(); // Efface tous les marqueurs affichés

      this.addMarkersAsCircles(this.chemin);
    },*/
    /*showAllMetroLines() {
      this.currentLine = null; // Réinitialise la ligne actuelle
      this.linesLayerGroup.clearLayers(); // Efface toutes les lignes affichées
      this.markersLayer.clearLayers(); // Efface tous les marqueurs affichés

      // Affiche toutes les lignes de métro disponibles
      for (const line in this.chemin) {
        this.addMetroLines(this.chemin, line);
        this.addMarkersWithColor(this.chemin, line);
      }
    },*/
    /*
    //Sert a toogle, quand on réapuis sur le menu déroulant pour revoir toute les lignes

    selectLine(line) {
      if (this.currentLine === line) {
        this.showAllLines(); // Si la ligne est déjà sélectionnée, afficher toutes les stations
      } else {
        this.toggleLine(line); // Sinon, afficher la ligne sélectionnée
      }
    },*/
    zoomToBounds(bounds) {
      if (bounds && bounds.isValid && bounds.isValid()) {
        this.map.fitBounds(bounds, { paddingTopLeft: [window.innerWidth * 0.2, 0] });
      }
    },
    clearMap() {
      this.linesLayerGroup.clearLayers();
      this.markersLayer.clearLayers();
    },


    traceChemin(cheminIndex) {
      console.log("fonction trace chemin");
      console.log(chemin_json.value);
      console.log("Chemin numéro : " + cheminIndex);

      let chemin_affiche = chemin_json.value.chemins[cheminIndex - 1];
      console.log("le chemin affiché est :");
      console.log(chemin_affiche);

      if (!chemin_affiche) {
        console.error("Chemin non trouvé pour l'index : " + cheminIndex);
        return;
      }

      console.log("Les lignes sont :");
      console.log(Object.keys(chemin_affiche));

      // Clear previous markers and lines
      this.clearMap();

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
            console.log("ligne key : " + lineKey)
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

            console.log("marker")
            console.log(marker)

            this.markersLayer.addLayer(marker);
            console.log("previous cord " + previousCoord);

            //Relier les stations de la même ligne
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

.line-button {
  margin-top: -10rem;
}

.marker-point {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  transition: transform 0.2s ease-in-out;
}

.marker-point:hover {
  transform: scale(1.8);
}

.triangle-icon {
  width: 0;
  height: 0;
  border-left: 5px solid transparent;
  border-right: 5px solid transparent;
  border-bottom: 10px solid grey;
}

.custom-div-icon {
  cursor: pointer;
}

.dropdown {
  position: fixed;
  /* Utiliser relative au lieu de absolute */
  margin-top: -4rem;
  margin-left: 50vw;
  display: inline-block;
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
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 2;
  /* Augmenter le z-index pour que le dropdown soit au-dessus du bouton */
  top: -40.3rem;
  /* Positionner le dropdown au-dessus du bouton */
  left: 50%;
  /* Centrer horizontalement par rapport au bouton */
  transform: translateX(-50%);
  border-radius: 15px;
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

.custom-div-icon {
  position: relative;
  background: #fff;
  border: 1px solid #333;
  text-align: center;
  font-size: 0.6rem;
  padding: 2px;
  border-radius: 5px;
}

.marker-point {
  width: auto;
  height: auto;
}
</style>
