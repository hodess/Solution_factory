<template>
  <div>
    <div id="map"></div>
    <div>
      <button @click="showAllLines">Afficher toutes les stations</button>
      <button v-for="(color, line) in lineColors" :key="line" @click="toggleLine(line)">
        {{ line }}
      </button>
      <button @click="showAllMetroLines">Afficher toutes les lignes d'un coup</button>
    </div>
  </div>
</template>

<script>
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import data from '../../public/data.json';

export default {
  name: 'LeafletMap',
  data() {
    return {
      markersLayer: null,
      linesLayerGroup: null,
      metroMarkers: [],
      triangleIcon: null,
      currentLine: null
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
    this.showAllLines(); // Appel de la méthode pour afficher les triangles noirs par défaut
  },
  methods: {
    initMap() {
      this.map = L.map('map').setView([48.8566, 2.3522], 13);

      L.tileLayer('https://api.maptiler.com/maps/positron/{z}/{x}/{y}.png?key=kGzEOK5vmVP8dEjh59c5', {
        attribution: '&copy; <a href="https://www.maptiler.com/copyright/">MapTiler</a>',
        tileSize: 512,
        zoomOffset: -1,
        minZoom: 0,
        maxZoom: 18,
        accessToken: 'kGzEOK5vmVP8dEjh59c5'
      }).addTo(this.map);

      this.linesLayerGroup = L.layerGroup().addTo(this.map);
      this.markersLayer = L.layerGroup().addTo(this.map);

      this.addMarkersAsTriangles(data.metro_paris);
    },
    addMarkersAsTriangles(metroData) {
      if (!this.triangleIcon) {
        return; // Si triangleIcon n'est pas encore initialisé, ne rien faire
      }

      const triangleIcon = this.triangleIcon;

      for (const line in metroData) {
        const stations = metroData[line].stations;
        stations.forEach(station => {
          const { latitude, longitude, nom } = station.coordonnees;
          const marker = L.marker([latitude, longitude], { icon: triangleIcon })
              .bindPopup(nom); // Bind popup with station name

          marker.on('click', () => {  // Add click event to open popup
            marker.openPopup();
          });

          this.markersLayer.addLayer(marker);
          this.metroMarkers.push({ line, marker });
        });
      }
    },


    addMetroLines(metroData, line) {
      const lineColor = this.lineColors[line] || 'black';

      for (const lineKey in metroData) {
        const stations = metroData[lineKey].stations;
        const polyline = L.polyline(stations.map(station => [station.coordonnees.latitude, station.coordonnees.longitude]), { color: lineColor });

        if (lineKey === line) {
          this.linesLayerGroup.addLayer(polyline);
        }
      }
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
        this.addMetroLines(data.metro_paris, this.currentLine);
        this.addMarkersWithColor(data.metro_paris, this.currentLine);
      } else {
        this.addMarkersAsTriangles(data.metro_paris);
      }
    },
    addMarkersWithColor(metroData, line) {
      const lineColor = this.lineColors[line] || 'black';

      for (const lineKey in metroData) {
        const stations = metroData[lineKey].stations;
        stations.forEach(station => {
          const { latitude, longitude, nom } = station.coordonnees;
          const customIcon = L.divIcon({
            className: 'custom-div-icon',
            html: `<div style="background-color: ${lineColor}" class="marker-point"></div>`,
            iconSize: [12, 12],
            iconAnchor: [6, 6]
          });

          if (lineKey === line) {
            const marker = L.marker([latitude, longitude], { icon: customIcon }).bindPopup(nom);
            this.markersLayer.addLayer(marker);
            this.metroMarkers.push({ line, marker });
          }
        });
      }
    },
    showAllLines() {
      this.currentLine = null; // Réinitialise la ligne actuelle
      this.linesLayerGroup.clearLayers(); // Efface toutes les lignes affichées
      this.markersLayer.clearLayers(); // Efface tous les marqueurs affichés

      this.addMarkersAsTriangles(data.metro_paris); // Réaffiche tous les triangles noirs
    },
    showAllMetroLines() {
      this.currentLine = null; // Réinitialise la ligne actuelle
      this.linesLayerGroup.clearLayers(); // Efface toutes les lignes affichées
      this.markersLayer.clearLayers(); // Efface tous les marqueurs affichés

      // Affiche toutes les lignes de métro disponibles
      for (const line in data.metro_paris) {
        this.addMetroLines(data.metro_paris, line);
        this.addMarkersWithColor(data.metro_paris, line); // Ajoute les marqueurs de couleur
      }
    }
  }
};
</script>
<style>
#map {
  height: 800px;
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
  border-bottom: 10px solid black;
}

.custom-div-icon {
  cursor: pointer;
}
</style>