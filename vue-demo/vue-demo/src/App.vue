<script>
import MainPage from "@/pages/mainPage.vue";

export default {
  name: 'App',
  components: {
    MainPage,
  },
};
</script>

<template>
  <div id="app">
    <mainPage />
  </div>
</template>



<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 20px;
}
</style>
