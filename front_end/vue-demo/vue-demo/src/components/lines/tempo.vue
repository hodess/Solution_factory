<template>
  <div id="map"></div>
</template>

<script>
import L from 'leaflet';
import "leaflet/dist/leaflet.css";

// Importer les données JSON
import data from '/Users/theotime/WebstormProjects/Solution_factory/vue-demo/vue-demo/public/data.json';

// Définir les couleurs des lignes de métro
const lineColors = {
  ligne_1: 'rgb(247, 207, 71)',
  ligne_2: 'rgb(42, 99, 169)',
  ligne_3: 'rgb(155, 153, 73)',
  ligne_4: 'rgb(174, 80, 152)',
  ligne_5: 'rgb(232, 149, 89)',
  ligne_6: 'rgb(137, 196, 153)',
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

export default {
  name: 'LeafletMap',
  mounted() {
    this.initMap();
  },
  methods: {
    initMap() {
      // Créer la carte
      this.map = L.map('map').setView([48.8566, 2.3522], 13);

      // Utiliser les tuiles MapTiler Positron
      L.tileLayer('https://api.maptiler.com/maps/positron/{z}/{x}/{y}.png?key=kGzEOK5vmVP8dEjh59c5', {
        attribution: '&copy; <a href="https://www.maptiler.com/copyright/">MapTiler</a>',
        tileSize: 512,
        zoomOffset: -1,
        minZoom: 0,
        maxZoom: 18,
        accessToken: 'kGzEOK5vmVP8dEjh59c5'
      }).addTo(this.map);

      // Ajouter les marqueurs pour chaque station de métro
      this.addStations(data.metro_paris);
    },
    addStations(metroData) {
      const maxZoomToShowMarkers = 12; // Niveau de zoom maximal pour afficher les marqueurs

      // Créer une icône personnalisée en forme de rond avec une taille qui change en fonction du zoom
      const customIcon = L.divIcon({
        className: 'custom-div-icon',
        html: "<div class='circle-icon'></div>",
        iconSize: this.getIconSize(this.map.getZoom()), // Utilisation de la fonction pour définir la taille initiale
        iconAnchor: [5, 5] // Ancrage de l'icône au centre
      });

      // Nettoyer les marqueurs initiaux qui ne sont pas censés être affichés au chargement
      this.map.eachLayer(layer => {
        if (layer instanceof L.Marker && layer.options.icon === customIcon) {
          if (this.map.getZoom() < maxZoomToShowMarkers) {
            layer.remove();
          }
        }
      });

      // Parcourir les lignes de métro
      for (const line in metroData) {
        const stations = metroData[line].stations;
        const latlngs = [];
        const lineColor = lineColors[line] || 'black'; // Utiliser la couleur définie ou noir par défaut

        // Ajouter un marqueur pour chaque station et collecter les coordonnées
        stations.forEach(station => {
          const { latitude, longitude } = station.coordonnees;
          latlngs.push([latitude, longitude]);

          const marker = L.marker([latitude, longitude], { icon: customIcon });

          // Ajouter un gestionnaire d'événements pour gérer l'ajout et la suppression des marqueurs en fonction du zoom
          this.map.on('zoomend', () => {
            if (this.map.getZoom() >= maxZoomToShowMarkers) {
              marker.addTo(this.map).bindPopup(station.nom);
            } else {
              marker.remove();
            }
          });

          // Ajouter les marqueurs initialement si le niveau de zoom le permet
          if (this.map.getZoom() >= maxZoomToShowMarkers) {
            marker.addTo(this.map).bindPopup(station.nom);
          }
        });

        // Ajouter une polyligne pour relier les stations de la ligne
        L.polyline(latlngs, { color: lineColor }).addTo(this.map);
      }
    },
    getIconSize(zoom) {
      // Définir une taille de marqueur dynamique en fonction du zoom
      const baseSize = 10; // Taille de base du marqueur
      return [baseSize * (zoom / 13), baseSize * (zoom / 13)]; // Ajustement de la taille en fonction du niveau de zoom
    }
  }
};
</script>

<style>
#map {
  height: 1400px;
}

.custom-div-icon {
  background: transparent;
}

.circle-icon {
  width: 10px;
  height: 10px;
  background-color: #fff; /* Couleur de fond blanche */
  border-radius: 50%; /* Forme ronde */
  border: 2px solid #000; /* Bordure de 2px solide noire */
  box-shadow: 0 0 2px #000; /* Légère ombre */
}
</style>
