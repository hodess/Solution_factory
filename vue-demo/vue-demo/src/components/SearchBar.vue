


<script setup>
import {gares} from "../stockage/gares.js";

console.log(gares.value);

</script>
<template>
  
<header class="navbar">MetroLine</header>

  <body>
    
    <div class="container">
      <div class="Back">
        <div class = "block_input">
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
        <div class ="block_input"> 
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
    align-items: center;
    border-radius: 15px;
    background-color: var(--vt-c-container-color) ;
    padding: 2rem;
    position: fixed;
    transform: translateX(-50%);
    width: fit-content;
    box-shadow: 0px 2px 4px var( --vt-c-black);
    z-index: 3;
  }

  .Back {
    flex-direction: column;
    align-items: center;
    flex: 0 0 30%;
    height: 90vh;
    z-index: 5;
    color: var( --vt-c-black);
  }
  

  .search {
    flex-grow: 1;
    min-width: 0;
    padding: 0.5rem 1rem;
    border-radius: 1rem;
    background-color: var(--vt-c-white);
    height: 3.5rem;
    margin-top: 1rem;
    font-size: 1.5rem;
    color: var( --vt-c-black);
  }

  .block_input{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 100%;
  }


  button {
    background-color: var(--vt-c-white);
    border: none;
    color: var( --vt-c-black);
    padding: 1rem 2rem;
    font-size: 1rem;
    border-radius: 2rem;
    margin-top: 1rem;
    font-size: 1.5rem;
      
    
  }

  .navbar {
    background-color: var(--vt-c-navbar-color);
    color: var(--vt-c-white);
    padding: 1rem;
    left: 0;
    top: 3.5vh;
    transform: translateY(-50%);
    text-align: center;
    font-size: 1.5rem;
    font-weight: bold;
    position: fixed;
    width: 100%;
    box-shadow: 0px 2px 4px var( --vt-c-black);
    z-index: 3;
  }
/*
  @media (max-width: 768px) {
    .container {
      width: 50vh;
      top: 50%;
    }

    .Back {
      flex: 0 0 50%;
      height: 70vh;
      width: 50vh;
    }

    .search {
      height: 2rem;
      width: 1rem;
      font-size: 1.6rem;

    }
  }*/


</style>
