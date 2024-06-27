<!-- src/components/HelloWorld.vue -->
<template>
  <div>
    <button @click="fetchData">Fetch Data</button>
    <p>{{ message }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      message: '',
    };
  },
  methods: {
    async fetchData() {
      try {
        const response = await axios.get('/api/greet');
        this.message = response.data;
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
  },
};




// api.js
/*
import axios from 'axios';

const backendUrl = 'http://localhost:8080'; // URL de votre backend Spring Boot

export async function calculerSomme(variable1, variable2) {
  try {
    const response = await axios.post(`${backendUrl}/calculer`, {
      variable1,
      variable2
    });
    return response.data;
  } catch (error) {
    console.error('Erreur lors de la requête vers le backend :', error);
    throw error;
  }
} */



import axios from 'axios';

const backendUrl = 'http://localhost:8080'; // Assurez-vous que c'est le bon port si nécessaire

export async function calculerSomme(variable1, variable2) {
  try {
    const response = await axios.post(`${backendUrl}/calculer`, {
      variable1,
      variable2,
    });
    return response.data;
  } catch (error) {
    console.error('Erreur lors de la requête vers le backend :', error);
    throw error;
  }
}

</script>
