<script setup>
  import MetroMap from '../components/MetroMap.vue';
  import pathBar from '../components/pathBar.vue';
  import { ref, onMounted } from 'vue';
  import axios from 'axios';

  const message = ref('Loading...');

  onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8081/init');
    message.value = response.data;
  } catch (error) {
    console.error('Error fetching data', error);
    message.value = 'Error loading data';
  }
});

const ShowConnexite = () => {
  axios.get('http://localhost:8081/connexite').then((response) => {
    if (response.data) {
      alert("Le graphe est bien connexe");
    }else{
      alert("Le graphe n'est pas connexe");
    }
  });
};

</script>

<template>

  <main>
    <div class="div1"><pathBar/></div>

    <div class="div2"><MetroMap/></div>

  <div class = "div1" id ="logo" @click="ShowConnexite"/>
  </main>

</template>

<style scoped>
  .div1{
    position: fixed;
    top: 0;
    left: 0;
    display: flex; /* Utilise flexbox pour le contenu de div1 */
    z-index: 1000; /* Définit la priorité d'affichage au-dessus de div2 */
    width: fit-content; /* Ajuste la largeur pour occuper toute la largeur de l'écran */
  }
  .div2{
    width: fit-content;
  }

  #logo{
    background-image: url("@/assets/logo-locomotive.gif");
    background-size: cover; /* Pour ajuster la taille de l'image à la fenêtre */
    background-position: center;
    color: #000;
    cursor: pointer;
    left: 200vh;
    height: 5rem;
    width: 5rem;
  }
</style>