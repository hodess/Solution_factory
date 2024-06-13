<script setup>
import SearchBar from '../components/SearchBar.vue';
import metroWorldMap from '../assets/metro-world-map.jpg';

console.log(metroWorldMap); // Vérifiez si le chemin est correct
</script>

<template >
  <main class="ImageFond">
    <SearchBar />
    <div class="pete_couille" />
  </main>
</template>

<style scoped>
.ImageFond {
  background-size: cover; /* Pour ajuster la taille de l'image à la fenêtre */
  background-position: center;
  z-index: -0.5;
  background-color: #2c3e50;

}

.pete_couille{
  background-image: url("@/assets/metro-world-map-upscaled.jpg");
  background-repeat: no-repeat;
  background-position: top;
  background-size: cover; /* Pour ajuster la taille de l'image à la fenêtre */
  position: fixed ;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100%;
}

</style>
