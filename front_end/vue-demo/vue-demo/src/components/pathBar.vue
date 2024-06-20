<script setup>
  import location from "@/components/location.vue";
  import paths from "@/components/paths.vue";
  import DepartDestination from "@/components/DepartDestination.vue";
  import SearchBar from "@/components/SearchBar.vue";

</script>

<template>
  <div id = 'all_nav_components'>
    <div class="left-component"><SearchBar/></div>
    <!-- <location /> -->
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
    background-color: transparent;
    justify-content: center;
    position: relative;
  }

  #separate{
  flex-grow: 1;
  }

  .left-component{
    margin-top: 1rem;
  }
</style>