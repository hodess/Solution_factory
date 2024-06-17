<template>
  <div>
    <input type="number" v-model.number="number1" placeholder="Enter first number">
    <input type="number" v-model.number="number2" placeholder="Enter second number">
    <button @click="multiplyNumbers">Multiply</button>
    <h1 class="white-text">Result: {{ result }}</h1>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      number1: null,
      number2: null,
      result: null
    };
  },
  methods: {
    multiplyNumbers() {
      if (!this.number1 || !this.number2) {
        alert('Please enter both numbers.');
        return;
      }
      
      axios.get(`http://localhost:8081/multiply/${this.number1}/${this.number2}`)
        .then(response => {
          this.result = response.data;
        })
        .catch(error => {
          console.error("There was an error!", error);
        });
    }
  },
  mounted() {
    // You can optionally perform some initialization here
  }
};
</script>

<style scoped>
.white-text {
  color: white;
}
</style>
