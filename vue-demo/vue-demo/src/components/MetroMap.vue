<script>
import { defineComponent, onMounted, ref } from 'vue';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

export default defineComponent({
  name: 'MetroMap',
  setup() {
    console.log('Component is being set up');

    const stations = ref([]);
    const imageBounds = [[0, 0], [952, 987]];

    const fetchStations = async () => {
      console.log('Fetching stations');
      try {
        const response = await fetch('/stations.txt');
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.text();
        console.log('Data fetched:', data);
        const lines = data.split('\n').filter(line => line.trim() !== '');
        stations.value = lines.map(line => {
          const [name, x, y] = line.split(',');
          console.log('Station parsed:', { name, x: parseFloat(x), y: parseFloat(y) });
          return { name, x: parseFloat(x), y: parseFloat(y) };
        });
      } catch (error) {
        console.error('Error fetching stations:', error);
      }
    };

    onMounted(async () => {
      console.log('Component is mounted');
      await fetchStations();

      const map = L.map('map', {
        crs: L.CRS.Simple,
        minZoom: -1,
        maxZoom: 1,
      });

      L.imageOverlay('/metro-map.png', imageBounds).addTo(map);

      map.fitBounds(imageBounds);

      stations.value.forEach(station => {
        const latLng = map.unproject([station.x*2, station.y*-2], map.getMaxZoom());
        const marker = L.circleMarker(latLng, {
          radius: 5,
          color: 'red',
        }).addTo(map);

        marker.bindPopup(station.name);
      });

      setTimeout(() => {
        if (!map.getBounds()) {
          console.error('The map did not initialize correctly.');
        }
      }, 1000);
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
.map-container {
  height: 100vh; /* Assurez-vous que la hauteur est bien définie pour les grands écrans */
  width: 100%;
}

#map {
  height: 100%;
  width: 100%;
}

/* Media queries pour gérer différentes tailles d'écran */
@media (min-width: 1020px) {
  .map-container {
    height: 100vh; /* Assurez-vous que la hauteur est bien définie pour les grands écrans */
    width: 100%;
  }
}



</style>
