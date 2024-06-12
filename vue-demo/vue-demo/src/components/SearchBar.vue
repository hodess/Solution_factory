


<script setup>
import {gares} from "../stockage/gares.js";

console.log(gares.value);

</script>
<template>
  
  <body>
    <div class="container">
      <div class="Back">
        <div class = "SearchBar">
          <input
            v-model="searchText"
            @input="updateHomeRouteQuery"
            @keypress.enter="navigateToHomeRoute"
            type="search"
            class="search"
            placeholder="Départ"
          />
          <input
            v-model="searchText"
            @input="updateHomeRouteQuery"
            @keypress.enter="navigateToHomeRoute"
            type="search"
            class="search"
            placeholder="Arrivé"

          />    
        </div>
        <div class =" DateHours"> 
          <input
            v-model="searchText"
            @input="updateHomeRouteQuery"
            @keypress.enter="navigateToHomeRoute"
            type="Date"
            class="search"
            placeholder="Date"
            />
            <input
            v-model="searchText"
            @input="updateHomeRouteQuery"
            @keypress.enter="navigateToHomeRoute"
            type="time"
            class="search"
            placeholder="Heure"
          />
      </div>
      <div class =" SearchButton"> 
        <button @click="navigateToHomeRoute">Go !</button>
      </div>
    </div>
    
    </div>
  </body>
</template>
<style scoped>

.container {
  display: flex;
  flex-direction: column;
  border-radius: 20px; /* Ajoute un fond arrondi sur les bords */
  background-color: rgb(219, 53, 81);
  padding: 20px; /* Ajoute un espacement à l'intérieur de la div */
  position: fixed; /* Fixe la position de la div */
  top: 37%; /* Place la div à 20 pixels du haut de la page */
  left: 50%;
  transform: translateX(-50%);
  width: 18%; /* Largeur de la div */
  z-index: 3;
}

.Back {
  flex : 0 0 30%;
  height: 90vh;
  width: 100v; 
  z-index: 5;
  color: black;

}

.search {
  flex-grow: 1;
  min-width: 0;
  width: 100%;
  padding: 0.5rem 1rem;
  font-size: 1rem;
  border-radius: 2rem;
  background-color: rgb(228, 210, 213);
  height: 5rem; /* Adjust the height as desired */
  margin-top: 1rem; /* Add margin to create space between text inputs */
  font-size: 2.2rem;
  color: black;

} 
button {
  background-color: rgb(228, 210, 213);
  border: none;
  color: black;
  padding: 1rem 2rem;
  font-size: 1.5rem;
  border-radius: 2rem;
  cursor: pointer;
  margin-top: 1rem;
  font-size: 2.2rem;
  color: black;
}

@media (max-width: 768px) {
  .container {
    width: 90%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
  
  .Back {
    flex: 0 0 50%;
    height: 70vh;
    
    width: 100%;
  }
  
  .search {
    height: 4rem;
    font-size: 1.6rem;
  }
  

}

</style>
