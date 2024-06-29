<script setup>
import SearchBar from '../components/SearchBar.vue';
import metroWorldMap from '../assets/metro-world-map.jpg';
import Favori from '../components/favori.vue';

console.log(metroWorldMap); // Vérifiez si le chemin est correct
</script>

<template >
  <header class="navbar">
    <div class="logo-locomotive"/>
    <div class="title-locomotive">Locomotive</div>
    <div class="bloc-right"/>
  </header>
  <main class="ImageFond">
    <div class="search_bar">
    <SearchBar />
    <Favori />
    </div>
    <div class="image" />
  </main>
</template>

<style scoped>
.ImageFond {
  height: 100%;
  margin: 0;
  display: flex;
  justify-content: center;
  align-items: flex-start; /* Aligne les enfants au début */
  /*
  background-image: url("@/assets/metro-world-map-upscaled.jpg");
  background-size: cover;
  background-position: center;
  z-index: 1.5;
  background-color: #2c3e50;*/
}

.search_bar {
  position: relative; /* Change de fixed à relative */
  z-index: 2;
  margin-top: 20vh; /* Décale de 50% de la hauteur de l'écran */
}




.image{
  background-image: url("@/assets/metro-world-map-upscaled.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  filter: blur(3px);
}



.navbar {
  background-color: var(--vt-c-navbar-color);
  color: var(--vt-c-white);
  padding: 1rem;
  left: 0;
  top: 3.5vh;
  transform: translateY(-50%);
  align-items: center;
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  position: fixed;
  display: flex;
  width: 100%;
  box-shadow: 0 2px 4px var( --vt-c-black);
  z-index: 3;
}
.logo-locomotive{
  background-image: url("@/assets/logo-locomotive.png");
  background-size: cover; /* Pour ajuster la taille de l'image à la fenêtre */
  background-position: center;
  z-index: -0.5;
  border-radius: 15px;
  height: 5rem;
  width: 5rem;
  margin-top: 3rem
}
.bloc-right{
  height: 5rem;
  width: 5rem;
  margin-top: 3rem
}

.title-locomotive {
  margin-top: 3rem;
  font-size: 1.8rem;
  flex: 1;
}


.title-trip{
  color: #f8f8f8;
  font-size: 1.2rem;
}
</style>
