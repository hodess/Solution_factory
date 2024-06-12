<script setup>
import SearchBar from '../components/SearchBar.vue';
import metroWorldMap from '../assets/metro-world-map.jpg';

console.log(metroWorldMap); // Vérifiez si le chemin est correct
</script>

<template>
  <main>
    <div :style="{ backgroundImage: `url(${metroWorldMap})` }" class="ImageFond">
      <SearchBar /> 
    </div>
  </main>
</template>

<style scoped>
.ImageFond {
  background-size: cover;
  background-repeat: no-repeat;
  height: 100vh; /* Ajouté pour assurer que le div ait une hauteur */
}

/* Media queries pour la réactivité */
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
