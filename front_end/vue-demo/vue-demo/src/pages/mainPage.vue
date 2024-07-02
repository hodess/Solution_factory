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

  <button class = "div1" id ="logo" @click="ShowConnexite">Fetch Data</button>
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
    background-color: #f8f8f8;
    color: #000;
    padding: 0.5rem 1rem;
    border-radius: 5px;
    cursor: pointer;
    left: 190vh;
    top: 1rem; /* Ajoutez cette ligne pour ajouter un espace entre le bouton et le bord droit */
    }
</style>