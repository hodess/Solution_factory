<template>
  <div>
    <input type="text" v-model="start" placeholder="Nom de la gare de départ">
    <input type="text" v-model="end" placeholder="Nom de la gare d'arrivée">
    <button @click="fetchAndLogResult">Fetch and Log Result</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      start: '',
      end: ''
    };
  },
  methods: {
    fetchAndLogResult() {
      axios.get(`http://localhost:8081/find_gare?start=${this.start}&end=${this.end}`)
        .then(response => {
          console.log(response.data); // Affiche la réponse dans la console
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
    }
  }
};
</script>

<style scoped>
/* Styles spécifiques au composant si nécessaire */
</style>
