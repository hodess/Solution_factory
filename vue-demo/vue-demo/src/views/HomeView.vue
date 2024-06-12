<script setup>
import SearchBar from '../components/SearchBar.vue'
</script>

<template>
  <main>
    <SearchBar />
    <div class =" ImageFond"> </div>
  
  </main>

 
</template>

<style scoped>
.ImageFond {
  background-image: url("../assets/metro-world-map.jpg");
  background-size: contain;
  height: 100vh;
  width: 203.1vh;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 2;
}

/* Media queries for responsiveness */
@media (max-width: 1200px) {
  .ImageFond {
    background-size: contain;
  }
}

@media (max-width: 768px) {
  .ImageFond {
    background-size: contain;
  }
}

@media (max-width: 480px) {
  .ImageFond {
    background-size: contain;
    background-position: center;
  }
}
</style>
