<script>
import { defineComponent, onMounted, ref } from 'vue';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

export default defineComponent({
  name: 'MetroMap',
  setup() {

    const stations = ref([]);
    const imageBounds = [[0, 0], [952, 987]];

    // recuperer les station d'un .json
    const fetchStations = async () => {
      try {
        const response = await fetch('/data.json');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        const parsedStations = [];

        // mettre tout les station dans une liste
        for (const line in data.metro_paris) {
          if (data.metro_paris[line].stations) {
            data.metro_paris[line].stations.forEach(station => {
              parsedStations.push({
                ligne:line,
                name: station.nom,
                x: station.coordonnees.x,
                y: 952-station.coordonnees.y
              });
            });
          }
        }

        stations.value = parsedStations;
      } catch (error) {
        console.error('Error fetching stations:', error);
      }
    };

    onMounted(async () => {
      await fetchStations();

      // crée la map
      const map = L.map('map', {
        crs: L.CRS.Simple,
        minZoom: -1,
      });

      L.imageOverlay('/metro.png', imageBounds).addTo(map);
      map.fitBounds(imageBounds);

      const lineStations = {};
      const allLatLngs = [];

      // ajouter les point a la map
      stations.value.forEach(station => {
        if (!lineStations[station.ligne]) {
          lineStations[station.ligne] = [];
        }
        const latLng = map.unproject([station.x*2, station.y*-2], 1);
        lineStations[station.ligne].push(latLng);
        allLatLngs.push(latLng);

        // ajouter de la couleur au station
        let color = 'white'; 
        if (station.ligne == 'ligne_1') color = 'yellow';
        if (station.ligne == 'ligne_2') color = 'blue';
        if (station.ligne == 'ligne_3') color = 'green';
        if (station.ligne == 'ligne_4') color = 'purple';
        if (station.ligne == 'ligne_5') color = 'orange';
        if (station.ligne == 'ligne_6') color = 'green';
        if (station.ligne == 'ligne_7') color = 'pink';
        const marker = L.circleMarker(latLng, {
          radius: 5,
          color: color,
          fillColor: color,
          fillOpacity: 1,
        }).addTo(map);
        marker.bindPopup(station.name);
      });
      
      // Ajouter des lignes entre les stations
      for (const line in lineStations) {
        let color = 'white'; // ajouter de la couleur au ligne
        if (line == 'ligne_1') color = 'yellow';
        if (line == 'ligne_2') color = 'blue';
        if (line == 'ligne_3') color = 'green';
        if (line == 'ligne_4') color = 'purple';
        if (line == 'ligne_5') color = 'orange';
        if (line == 'ligne_6') color = 'green';
        if (line == 'ligne_7') color = 'pink';
        L.polyline(lineStations[line], {
          color: color,
          weight: 2
        }).addTo(map);
      }

      // Ajuster le zoom 
      if (allLatLngs.length) {
        let bounds = L.latLngBounds(allLatLngs);
        map.fitBounds(bounds);
      }
    });

    return {};
  },
});
</script>

<template>
  <div class="map-container">
    <div id="map"></div>
  </div>
</template>



<style>
#app{
  padding: 0;
}
.map-container {
  height: 100vh;
  width: 100%;
}

#map {
  height: 100%;
  width: 100%;
}
.leaflet-container {
    background: #000;
    outline-offset: 1px;
}
/* Media queries pour gérer différentes tailles d'écran */
@media (min-width: 1020px) {
  .map-container {
    height: 100vh;
    width: 100%;
  }
}


</style>
