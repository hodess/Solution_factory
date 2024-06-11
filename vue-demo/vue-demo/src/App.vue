<template>
  <div id="app">
    <MetroMap />
  </div>
</template>

<script>
import MetroMap from './components/MetroMap.vue';

export default {
  name: 'App',
  components: {
    MetroMap,
  },
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>