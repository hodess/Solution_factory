<script setup>
  import MetroMap from '../components/MetroMap.vue';
  import pathBar from '../components/pathBar.vue';
  import { ref, onMounted } from 'vue';
  import axios from 'axios';

  const message = ref('Loading...');

  onMounted(async () => {
  try {
    const response = await axios.get('http://localhost:8081/init/1');
    message.value = response.data;
  } catch (error) {
    console.error('Error fetching data', error);
    message.value = 'Error loading data';
  }
});

</script>

<template>

  <main>
    <div class="div1"><pathBar/></div>

    <div class="div2"><MetroMap/></div>
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
</style>