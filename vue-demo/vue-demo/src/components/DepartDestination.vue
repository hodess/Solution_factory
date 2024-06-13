<script setup>
</script>


<template>
  <div class = "block_input">
  <div class="title-trip">Où voulez-vous aller ?</div>
  <div class="small-title">De :</div>
  <input
      v-model="searchText"
      @input="updateHomeRouteQuery"
      @keypress.enter="navigateToHomeRoute"
      type="search"
      class="search"
      placeholder="Gare de départ"
  />

  <div class="small-title">A : </div>
  <input
      v-model="searchText"
      @input="updateHomeRouteQuery"
      @keypress.enter="navigateToHomeRoute"
      type="search"
      class="search"
      placeholder="Gare d'arrivée"
  />
  </div>
</template>


<style scoped>

.search {
  flex-grow: 1;
  min-width: 0;
  padding: 0.5rem 1rem;
  border-radius: 1rem;
  background-color: #f8f8f8;
  height: 3.5rem;
  margin-top: 1rem;
  font-size: 1rem;
  color: var( --vt-c-black);
  align-items: center;
  width: 20rem;
  border-width: 0;
}



.block_input{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 2rem;
}

.title-trip{
  color: #f8f8f8;
  font-size: 1.2rem;
}

.small-title{
  margin-top: 1rem;
  margin-bottom: -0.8rem;
  color: #f8f8f8;
}

</style>