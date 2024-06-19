<script setup>
import { stations } from "@/stockage/gares";
import { ref, computed } from 'vue';

function useSearch(stations) {
  const searchText = ref('');
  const searchResults = computed(() => {
    if (!searchText.value) {
      return [];
    }
    return stations.filter(station =>
      station.name.toLowerCase().includes(searchText.value.toLowerCase()) ||
      station.address.toLowerCase().includes(searchText.value.toLowerCase())
    ).slice(0, 4); // Limite à 2 résultats
  });

  return {
    searchText,
    searchResults,
  };
}

const { searchText: searchTextDepart, searchResults: searchResultsDepart } = useSearch(stations);
const { searchText: searchTextArrivee, searchResults: searchResultsArrivee } = useSearch(stations);

const isDepartFocused = ref(false);
const isArriveeFocused = ref(false);

function selectStation(station, type) {
  if (type === 'depart') {
    searchTextDepart.value = station.name;
    isDepartFocused.value = false;
    // Supprimer la gare sélectionnée de searchResultsDepart pour éviter la duplication
    searchResultsDepart.value = searchResultsDepart.value.filter(result => result.name !== station.name);
  } else if (type === 'arrivee') {
    searchTextArrivee.value = station.name;
    isArriveeFocused.value = false;
    // Supprimer la gare sélectionnée de searchResultsArrivee pour éviter la duplication
    searchResultsArrivee.value = searchResultsArrivee.value.filter(result => result.name !== station.name);
  }
  return{
    searchTextDepart,
    searchTextArrivee,
  };
}
</script>

<template>
  <div class="block_input">
    <div class="title-trip">Où voulez-vous aller ?</div>
    <div class="small-title">De :</div>
    <input
      :value="searchTextDepart"
      @input="searchTextDepart = $event.target.value"
      @focus="isDepartFocused = true"
      @blur="() => setTimeout(() => isDepartFocused = false, 100)"
      type="search"
      class="search"
      placeholder="Gare de départ"
    />
    <ul v-if="isDepartFocused && searchResultsDepart.length">
      <li v-for="result in searchResultsDepart" :key="result.name" @click="selectStation(result, 'depart')">
        <strong>{{ result.name }}</strong><br>
        {{ console.log(searchTextDepart) }}

      </li>
    </ul>

    <div class="small-title">A :</div>
    <input
      :value="searchTextArrivee"
      @input="searchTextArrivee = $event.target.value"
      @focus="isArriveeFocused = true"
      @blur="() => setTimeout(() => isArriveeFocused = false, 100)"
      type="search"
      class="search"
      placeholder="Gare d'arrivée"
    />
    <ul v-if="isArriveeFocused && searchResultsArrivee.length">
      <li v-for="result in searchResultsArrivee" :key="result.name" @click="selectStation(result, 'arrivee')">
        <strong>{{ result.name }}</strong><br>
        {{ console.log(searchTextArrivee) }}

      </li>
    </ul>
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
  color: var(--vt-c-black);
  align-items: center;
  width: 20rem;
  border-width: 0;
}

.block_input {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 2rem;
}

.title-trip {
  color: #f8f8f8;
  font-size: 1.2rem;
}

.small-title {
  margin-top: 1rem;
  margin-bottom: -0.8rem;
  color: #f8f8f8;
}

ul {
  list-style: none;
  padding: 0;
  margin-top: 1rem;
  background-color: #fff;
  border-radius: 1rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

li {
  padding: 0.5rem 1rem;
  border-bottom: 1px solid #ddd;
  color: black;
  cursor: pointer; /* Ajout d'un curseur pointeur pour indiquer que l'élément est cliquable */
}

li:last-child {
  border-bottom: none;
}
</style>
