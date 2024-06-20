<script setup>
import SearchBar from '../pages/SearchBar.vue';
import metroWorldMap from '../assets/metro-world-map.jpg';

console.log(metroWorldMap); // Vérifiez si le chemin est correct
</script>

<template >
  <main class="ImageFond">
    <SearchBar />
    <div class="image" />
  </main>
</template>

<style scoped>
.ImageFond {
  background-image: url("@/assets/metro-world-map-upscaled.jpg");
  background-size: cover; /* Pour ajuster la taille de l'image à la fenêtre */
  background-position: center;
  z-index: 1.5;
  background-color: #2c3e50;
}

.image{
  background-image: url("@/assets/metro-world-map-upscaled.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  position: fixed ;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100%;
  filter: blur(3px);
}
</style>
