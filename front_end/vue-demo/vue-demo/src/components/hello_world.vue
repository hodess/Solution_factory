<template>
  <div>
    <button @click="fetchAndLogResult">Fetch and Log Result</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  methods: {
    fetchAndLogResult() {
      axios.get('http://localhost:8081/gares')
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
