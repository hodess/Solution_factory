<script setup>
  import location from "@/components/location.vue";
  import paths from "@/components/paths.vue";

</script>

<template>
  <div id = 'all_nav_components'>
    <location />
    <div id="separate" />
    <paths />
  </div>
</template>

<style scoped>
  #all_nav_components{
    display: flex;
    flex-direction: row;
    height: 20vh;
    width: 100%;
    background-color: rgb(0, 0, 0);
    justify-content: center;
    position: relative;
  }

  #separate{
    width: 30%;
  }
</style>