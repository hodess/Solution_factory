<script>
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import Hello_world from "@/components/hello_world.vue";
import { ref } from 'vue'
import axios from "axios"; // Importer les données des trajets

let chemin_json=ref(null)




function convertLineFormat(line) {
  switch (line) {
    case "Ligne 1":
      return "ligne_1";
    case "Ligne 2":
      return "ligne_2";
    case "Ligne 3":
      return "ligne_3";
    case "Ligne 3bis":
      return "ligne_3bis";
    case "Ligne 4":
      return "ligne_4";
    case "Ligne 5":
      return "ligne_5";
    case "Ligne 6":
      return "ligne_6";
    case "Ligne 7":
      return "ligne_7";
    case "Ligne 7bis":
      return "ligne_7bis";
    case "Ligne 8":
      return "ligne_8";
    case "Ligne 9":
      return "ligne_9";
    case "Ligne 10":
      return "ligne_10";
    case "Ligne 11":
      return "ligne_11";
    case "Ligne 12":
      return "ligne_12";
    case "Ligne 13":
      return "ligne_13";
    case "Ligne 14":
      return "ligne_14";
    default:
      return "feur"; // Couleur par défaut si la ligne n'est pas trouvée
  }
}



export default {
  name: 'LeafletMap',
  components: { Hello_world },
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
        'ligne_4': 'src/components/lines/metro-4.png',
        'ligne_5': 'src/components/lines/metro-5.png',
        'ligne_6': 'src/components/lines/metro-6.png',
        'ligne_7': 'src/components/lines/metro-7.png',
        'ligne_8': 'src/components/lines/metro-8.png',
        'ligne_9': 'src/components/lines/metro-9.png',
        'ligne_10': 'src/components/lines/metro-10.png',
        'ligne_11': 'src/components/lines/metro-11.png',
        'ligne_12': 'src/components/lines/metro-12.png',
        'ligne_13': 'src/components/lines/metro-13.png',
        'ligne_14': 'src/components/lines/metro-14.png',
      }
    };
  },
  computed: {
    lineColors() {
      return {
        ligne_1: '#FFCD00',     // Jaune
        ligne_2: '#003CA6',     // Bleu foncé
        ligne_3: '#837902',     // Vert olive
        ligne_3bis: '#6EC4E8',  // Bleu ciel
        ligne_4: '#CF009E',     // Fuchsia
        ligne_5: '#FF7E2E',     // Orange
        ligne_6: '#6ECA97',     // Vert clair
        ligne_7: '#FA9ABA',     // Rose
        ligne_7bis: '#B5BD00',  // Jaune vert
        ligne_8: '#E19BDF',     // Lilas
        ligne_9: '#B6BD00',     // Vert pomme
        ligne_10: '#C9910D',    // Orange clair
        ligne_11: '#704B1C',    // Marron
        ligne_12: '#007852',    // Vert bouteille
        ligne_13: '#6EC4E8',    // Bleu ciel (identique à la 3bis)
        ligne_14: '#62259D'     // Violet
      };
    }
  },
  mounted() {
    this.initMap();
    this.createTriangleIcon();
    this.showAllLines(); // Afficher tous les ronds noirs par défaut
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

      this.addMarkersAsCircles(this.chemin_json);

    },
    fetchAndLogResult() {
      axios.get(`http://localhost:8085/find_gare?start=Châtelet&end=Odéon`)
          .then(response => {
            console.log(response.data); // Affiche la réponse dans la console
            chemin_json=response.data
            console.log(chemin_json.chemins[1])
            let secondChemin = chemin_json.chemins[1]
            for (let key in secondChemin) {
              if (secondChemin[key].Gare) {
                secondChemin[key].Gare.forEach(gare => {
                  console.log(gare.coord);
                });
              }
            }
            console.log(secondChemin.temps)
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
    addMarkersAsCircles(metroData) {
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
    },
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
    },
    createTriangleIcon() {
      this.triangleIcon = L.divIcon({
        className: 'custom-div-icon',
        html: "<div class='triangle-icon'></div>",
        iconSize: [10, 10],
        iconAnchor: [5, 5]
      });
    },
    toggleLine(line) {
      this.currentLine = line;
      this.linesLayerGroup.clearLayers();
      this.markersLayer.clearLayers();

      if (this.currentLine) {
        const bounds = this.addMetroLines(this.chemin, this.currentLine);
        this.addMarkersWithColor(this.chemin, this.currentLine);

        this.zoomToBounds(bounds);
      }
    },
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
    },
    showAllLines() {
      this.currentLine = null; // Réinitialise la ligne actuelle
      this.linesLayerGroup.clearLayers(); // Efface toutes les lignes affichées
      this.markersLayer.clearLayers(); // Efface tous les marqueurs affichés

      this.addMarkersAsCircles(this.chemin);
    },
    showAllMetroLines() {
      this.currentLine = null; // Réinitialise la ligne actuelle
      this.linesLayerGroup.clearLayers(); // Efface toutes les lignes affichées
      this.markersLayer.clearLayers(); // Efface tous les marqueurs affichés

      // Affiche toutes les lignes de métro disponibles
      for (const line in this.chemin) {
        this.addMetroLines(this.chemin, line);
        this.addMarkersWithColor(this.chemin, line);
      }
    },
    selectLine(line) {
      if (this.currentLine === line) {
        this.showAllLines(); // Si la ligne est déjà sélectionnée, afficher toutes les stations
      } else {
        this.toggleLine(line); // Sinon, afficher la ligne sélectionnée
      }
    },
    zoomToBounds(bounds) {
      if (bounds && bounds.isValid && bounds.isValid()) {
        this.map.fitBounds(bounds, {paddingTopLeft: [window.innerWidth * 0.2, 0]});
      }
    },
    clearMap() {
      this.linesLayerGroup.clearLayers();
      this.markersLayer.clearLayers();
    },
    traceChemin(cheminIndex) {
      let chemin = chemin_json.chemins[cheminIndex-1]
      console.log(chemin)

      if (chemin) {
        this.clearMap(); // Efface les lignes et marqueurs existants sur la carte

        let bounds = new L.LatLngBounds(); // Initialise les limites pour calculer le zoom

        for (let key in chemin) {
          if (chemin.hasOwnProperty(key) && chemin[key].Gare) {
            let gares = chemin[key].Gare;
            let previousCoord=null
            let previousLine=null

            gares.forEach(gare => {
              const coord = gare.coord;
              const temp = coord[0]
              coord[0]=coord[1]
              coord[1]=temp

              if (coord && coord.length === 2) {
                const lineKey = convertLineFormat("Ligne"+key); // Convertir la ligne au format utilisé dans lineColors
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

                this.markersLayer.addLayer(marker);
                console.log("previous cord "+previousCoord)

                // Relier les stations de la même ligne
                if (previousCoord && previousLine === lineKey) {
                  const polyline = L.polyline([previousCoord, coord], { color: color }).addTo(this.map);
                  this.linesLayerGroup.addLayer(polyline);
                }

                previousCoord = [coord[0], coord[1]];
                previousLine = lineKey;
              }
              });
            }
          }
        }
      }
    },
};

const rotateButton = () => {
  if (dropdownButton.value) {
    // Définir la transition CSS
    dropdownButton.value.style.transition = 'transform 0.3s, background-color 0.3s';

    if (!dropdownButton.value.classList.contains('rotated')) {
      // Si le bouton n'est pas déjà tourné, le tourner
      dropdownButton.value.style.transform = 'rotate(180deg)';
      dropdownButton.value.classList.add('rotated');
    } else {
      // Sinon, le remettre à sa position d'origine
      dropdownButton.value.style.transform = '';
      dropdownButton.value.classList.remove('rotated');
    }
  }
};
</script>

<template>
  <div>
    <div id="map"></div>
    <div>
      <button @click="showAllLines">Afficher toutes les stations</button>
      <button @click="showAllMetroLines">Afficher toutes les lignes d'un coup</button>
      <button @click="traceChemin(1)">Tracer le premier chemin</button>
      <button @click="traceChemin(2)">Tracer le deuxième chemin</button>      <!-- Dropdown menu -->
      <button @click="fetchAndLogResult">recup</button>
      <div class="dropdown">
        <button class="dropbtn"></button>
        <div class="dropdown-content">
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

.line-button{
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
  position: fixed; /* Utiliser relative au lieu de absolute */
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
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 2; /* Augmenter le z-index pour que le dropdown soit au-dessus du bouton */
  top: -40.3rem; /* Positionner le dropdown au-dessus du bouton */
  left: 50%; /* Centrer horizontalement par rapport au bouton */
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
