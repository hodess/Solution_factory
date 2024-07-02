<script setup>
import location from "@/components/location.vue";
import paths from "@/components/paths.vue";
import DepartDestination from "@/components/DepartDestination.vue";
import SearchBar from "@/components/SearchBar.vue";
import SearchBarSimple from "@/components/SearchBarSimple.vue";
import { ref, watch } from "vue";

const dropdownButton = ref(null);
const showSearchBar = ref(false); // Variable réactive pour gérer l'état du composant

const handleClick = () => {
  toggleSearchBar();
  toggleHeureDepartArrivee();
};

const toggleSearchBar = () => {
  showSearchBar.value = !showSearchBar.value;
};

const toggleHeureDepartArrivee = () => {
  showHeureDepartArrivee.value = !showHeureDepartArrivee.value;
};

watch(showSearchBar, (newValue) => {
  rotateButton(newValue);
});

const rotateButton = (isSearchBarVisible) => {
  if (dropdownButton.value) {
    // Définir la transition CSS
    dropdownButton.value.style.transition = 'transform 0.3s, background-color 0.3s';

    if (isSearchBarVisible) {
      // Tourner le bouton de 180 degrés quand SearchBar est affiché
      dropdownButton.value.style.transform = 'rotate(180deg)';
    } else {
      // Remettre le bouton à sa position d'origine quand SearchBarSimple est affiché
      dropdownButton.value.style.transform = '';
    }
  }
};

const Mapordemarre = localStorage.getItem('currentDepart')
console.log("Map demarre : ",Mapordemarre)


const handleSearchBarSimpleClick = () => {
  if (!showSearchBar.value) {
    toggleSearchBar();
  }
};  
</script>

<template>
  <div id="all_nav_components">
    <div class="left-component" v-if="Mapordemarre" >
      <SearchBarSimple v-if="!showSearchBar" @click="handleSearchBarSimpleClick"/>
      <SearchBar v-else />
      <div class="left-button-component-container">
        <button ref="dropdownButton" @click="handleClick" class="left-button-component" />
      </div>
    </div>
    <div class="left-component" v-else >
      <SearchBar/>
    </div>

    <!-- <location /> -->
    <div id="separate" />
    <div class="right-component">
      <paths />
    </div>
  </div>
</template>

<style scoped>
#all_nav_components {
  display: flex;
  flex-direction: column;
  height: fit-content;
  width: fit-content;
  background-color: transparent;
  justify-content: center;
  position: relative;
}

#separate {
  flex-grow: 1;
}

.left-component {
  padding: 1rem;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  width: fit-content;
}

.left-button-component {
  position: relative;
  padding: 1.2rem 1.2rem;
  color: white;
  left: 2rem;
  bottom: 1.4rem;

  border-radius: 2rem;
  border-width: 0;
  background-color: rgb(0, 90, 255);
  height: 1rem;
  background-image: url("../assets/fleche-vers-le-bas.png");
  background-position: center;
  background-size: 1.2rem;
  background-repeat: no-repeat;
  filter: invert(1);
  width: 1rem;
  transition: transform 0.3s, background-color 0.3s;

  z-index: 1000000000;
}

  .left-button-component:hover {
  transform: scale(1.05);
}


.right-component{
  padding: 1rem;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  width: fit-content;
}




.dropdown-button-text {
  color: white;
}

.left-button-component-container {
  top: 1rem;
  right: 4rem;
  display: flex;
  width: fit-content;
  align-items: center;
  position: relative;
  height: 2.5rem;
}

.left-button-component-container-sticky {
  display: flex;
  position: absolute;
  top: 0;
  right: 0;
}
</style>
