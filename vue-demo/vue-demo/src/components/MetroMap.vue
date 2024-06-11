<template>
    <div id="map" :style="{ height: '100vh', width: '100%' }"></div>
  </template>
  
  <script>
  import L from 'leaflet';
  import 'leaflet/dist/leaflet.css';
  
  export default {
    name: 'MetroMap',
    data() {
      return {
        map: null,
        stations: [
          { name: 'Station 1', x: 100, y: 200 },
          { name: 'Station 2', x: 300, y: 400 },
          // Ajoutez les autres stations ici
        ],
        imageBounds: [[0, 0], [1000, 1000]], // Ajustez en fonction de la taille de votre image
      };
    },
    mounted() {
      this.initMap();
    },
    methods: {
      initMap() {
        // Créez la carte
        this.map = L.map('map', {
          crs: L.CRS.Simple,
          minZoom: -2,
        });
  
        // Ajouter l'image comme couche
        L.imageOverlay('path/to/your/image.png', this.imageBounds).addTo(this.map);
  
        // Ajustez la vue initiale de la carte
        this.map.fitBounds(this.imageBounds);
  
        // Ajoutez les stations
        this.stations.forEach(station => {
          const marker = L.circleMarker(this.map.unproject([station.x, station.y], this.map.getMaxZoom()), {
            radius: 5,
            color: 'red',
          }).addTo(this.map);
  
          marker.bindPopup(station.name);
        });
      },
    },
  };
  </script>
  
  <style>
  #map {
    height: 100vh;
    width: 100%;
  }
  </style>
  