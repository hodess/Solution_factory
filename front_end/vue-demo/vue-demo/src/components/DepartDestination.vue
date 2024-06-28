<script setup>
import axios from 'axios';
import { ref, computed, onMounted } from 'vue';

let stations = ref([]);

async function fetchStations() {
  try {
    const response = await axios.get('http://localhost:8081/gares');
    stations.value = response.data;
    console.log(stations.value);
    for (let key in stations.value) {
      if (stations.value[key].name) {
        // console.log(stations.value[key].name);
      }
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des gares:', error);
  }
}

// Appeler la fonction fetchStations au montage du composant
onMounted(fetchStations);

// Pour la navigation avec boutons
const selectedDepartIndex = ref(-1);
const selectedArriveeIndex = ref(-1);

function navigateResults(type, direction) {
  if (type === 'depart') {
    if (searchResultsDepart.value.length === 0) return;
    selectedDepartIndex.value = (selectedDepartIndex.value + direction + searchResultsDepart.value.length) % searchResultsDepart.value.length;
  } else if (type === 'arrivee') {
    if (searchResultsArrivee.value.length === 0) return;
    selectedArriveeIndex.value = (selectedArriveeIndex.value + direction + searchResultsArrivee.value.length) % searchResultsArrivee.value.length;
  }
}

function selectHighlightedResult(type) {
  if (type === 'depart' && selectedDepartIndex.value !== -1) {
    selectStation(searchResultsDepart.value[selectedDepartIndex.value], 'depart');
    selectedDepartIndex.value = -1;
  } else if (type === 'arrivee' && selectedArriveeIndex.value !== -1) {
    selectStation(searchResultsArrivee.value[selectedArriveeIndex.value], 'arrivee');
    selectedArriveeIndex.value = -1;
  }
}

function useSearch(stations) {
  const searchText = ref('');
  const searchResults = computed(() => {
    if (!searchText.value) {
      return [];
    }
    return stations.value
      .filter(station =>
        station.name.toLowerCase().includes(searchText.value.toLowerCase())
      )
      .slice(0, 4);
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
    searchResultsDepart.value = searchResultsDepart.value.filter(result => result.name !== station.name);
  } else if (type === 'arrivee') {
    searchTextArrivee.value = station.name;
    isArriveeFocused.value = false;
    searchResultsArrivee.value = searchResultsArrivee.value.filter(result => result.name !== station.name);
  }
  return {
    searchTextDepart,
    searchTextArrivee,
  }
}

function getStationLines(lines) {
  const lineMap = {
    // Metro lines
    '1': 'src/components/lines/metro-1.png',
    '2': 'src/components/lines/metro-2.png',
    '3': 'src/components/lines/metro-3.png',
    '4': 'src/components/lines/metro-4.png',
    '5': 'src/components/lines/metro-5.png',
    '6': 'src/components/lines/metro-6.png',
    '7': 'src/components/lines/metro-7.png',
    '8': 'src/components/lines/metro-8.png',
    '9': 'src/components/lines/metro-9.png',
    '10': 'src/components/lines/metro-10.png',
    '11': 'src/components/lines/metro-11.png',
    '12': 'src/components/lines/metro-12.png',
    '13': 'src/components/lines/metro-13.png',
    '14': 'src/components/lines/metro-14.png',
    // RER lines
    'A': 'src/components/lines/rer-a.png',
    'B': 'src/components/lines/rer-b.png',
    'C': 'src/components/lines/rer-c.png',
    'D': 'src/components/lines/rer-d.png',
    'E': 'src/components/lines/rer-e.png',

  };

  const metroLines = [];
  const rerLines = [];
  const tramLines = [];
  const transilienLines = [];
  const logos = [];

  lines.split(', ').forEach(line => {
    const formattedLine = line.trim();
    if (lineMap[formattedLine]) {
      if (/^\d+$/.test(formattedLine)) { // Commence par un chiffre de 1 à 9 (métro)
        metroLines.push(lineMap[formattedLine]);
      } else if (/^[A-Z]$/.test(formattedLine)) { // Commence par une lettre majuscule (RER)
        rerLines.push(lineMap[formattedLine]);
      } else if (formattedLine.startsWith('Tram')) {
        tramLines.push(lineMap[formattedLine]);
      } else if (formattedLine.startsWith('transilien')) {
        transilienLines.push(lineMap[formattedLine]);
      } else {
        logos.push(lineMap[formattedLine]);
      }
    } else {
      console.log(`Image not found for line: ${formattedLine}`);
    }
  });

  return {
    metroLines,
    rerLines,
    tramLines,
    transilienLines,
    logos
  };
}

const { metroLines, rerLines, tramLines, transilienLines, logos } = getStationLines('M1, RER A, Tram 1, transilien H, Logo Metro, Logo RER, Logo tram, Logo transilien');
</script>

<template>
  <div class="block_input">

    <!-- Departure input -->
    <!-- <div class="small-title">De :</div> -->
    <input v-model="searchTextDepart" @focus="isDepartFocused = true"
      @blur="() => setTimeout(() => isDepartFocused = false, 100)" @keydown.down.prevent="navigateResults('depart', 1)"
      @keydown.up.prevent="navigateResults('depart', -1)" @keydown.enter.prevent="selectHighlightedResult('depart')"
      type="search" class="search" placeholder="Gare de départ" />

    <ul v-if="isDepartFocused && searchResultsDepart.length">
      <li v-for="(result, index) in searchResultsDepart" :key="result.name" @click="selectStation(result, 'depart')"
        :class="{ 'double-height': getStationLines(result.nameLigne).metroLines.length && getStationLines(result.nameLigne).rerLines.length && getStationLines(result.nameLigne).tramLines.length && getStationLines(result.nameLigne).transilienLines.length, 'highlighted': index === selectedDepartIndex }">
        <strong>{{ result.name }}</strong><br>
        <div class="lines-container">
          <div class="metro-lines" v-if="getStationLines(result.nameLigne).metroLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-metro.png) ` }"></div>
            <template v-for="line in getStationLines(result.nameLigne).metroLines">
              <div v-if="line.type === 'logo'" class="logo-line" :style="{ backgroundImage: `url(${line.image})` }">
              </div>
              <div v-else class="logo-line" :style="{ backgroundImage: `url(${line})` }"></div>
            </template>
          </div>
          <div class="rer-lines" v-if="getStationLines(result.nameLigne).rerLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-RER.png) ` }"></div>
            <template v-for="line in getStationLines(result.nameLigne).rerLines">
              <div v-if="line.type === 'logo'" class="logo-line" :style="{ backgroundImage: `url(${line.image})` }">
              </div>
              <div v-else class="logo-line" :style="{ backgroundImage: `url(${line})` }"></div>
            </template>
          </div>
          <div class="tram-lines" v-if="getStationLines(result.nameLigne).tramLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-tram.png) ` }"></div>
            <template v-for="line in getStationLines(result.nameLigne).tramLines">
              <div v-if="line.type === 'logo'" class="logo-line" :style="{ backgroundImage: `url(${line.image})` }">
              </div>
              <div v-else class="logo-line" :style="{ backgroundImage: `url(${line})` }"></div>
            </template>
          </div>
          <div class="transilien-lines" v-if="getStationLines(result.nameLigne).transilienLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-transilien.png) ` }"></div>
            <template v-for="line in getStationLines(result.nameLigne).transilienLines">
              <div v-if="line.type === 'logo'" class="logo-line" :style="{ backgroundImage: `url(${line.image})` }">
              </div>
              <div v-else class="logo-line" :style="{ backgroundImage: `url(${line})` }"></div>
            </template>
          </div>

        </div>
      </li>
    </ul>

    <!-- Arrival input -->
    <!-- <div class="small-title">À :</div> -->
    <input v-model="searchTextArrivee" @focus="isArriveeFocused = true"
      @blur="() => setTimeout(() => isArriveeFocused = false, 100)"
      @keydown.down.prevent="navigateResults('arrivee', 1)" @keydown.up.prevent="navigateResults('arrivee', -1)"
      @keydown.enter.prevent="selectHighlightedResult('arrivee')" type="search" class="search"
      placeholder="Gare d'arrivée" />
    <ul v-if="isArriveeFocused && searchResultsArrivee.length">
      <li v-for="(result, index) in searchResultsArrivee" :key="result.name" @click="selectStation(result, 'arrivee')"
        :class="{ 'double-height': getStationLines(result.nameLigne).metroLines.length && getStationLines(result.nameLigne).rerLines.length && getStationLines(result.nameLigne).tramLines.length && getStationLines(result.nameLigne).transilienLines.length, 'highlighted': index === selectedArriveeIndex }">
        <strong>{{ result.name }}</strong><br>
        <div class="lines-container">
          <div class="metro-lines" v-if="getStationLines(result.nameLigne).metroLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-metro.png) ` }"></div>
            <template v-for="image in getStationLines(result.nameLigne).metroLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
          <div class="rer-lines" v-if="getStationLines(result.nameLigne).rerLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-RER.png) ` }"></div>
            <template v-for="image in getStationLines(result.nameLigne).rerLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
          <div class="tram-lines" v-if="getStationLines(result.nameLigne).tramLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-tram.png) ` }"></div>
            <template v-for="image in getStationLines(result.nameLigne).tramLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
          <div class="transilien-lines" v-if="getStationLines(result.nameLigne).transilienLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-transilien.png) ` }"></div>
            <template v-for="image in getStationLines(result.nameLigne).transilienLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
        </div>
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
  border: 1px solid #000000;

}

.block_input {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 2rem;
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
  border-radius: 0.7rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

li {
  padding: 0.5rem 1rem;
  border-bottom: 1px solid #ddd;
  color: black;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

li:last-child {
  border-bottom: none;
}

strong {
  max-width: 60%;
}

.logo-line {
  background-size: cover;
  background-repeat: no-repeat;
  width: 1rem;
  height: 1rem;
  margin-top: 3px;
  margin-right: 3px;
  margin-bottom: 3px;
}


.lines-container {
  display: flex;
  flex-direction: column;
}





.double-height {
  height: 4rem;
  /* Adjust this value as needed */
}


.lines-container {

  flex-direction: column;
}

.metro-lines,
.tram-lines,
.transilien-lines,
.rer-lines {
  display: flex;
  justify-content: flex-end;
}

.logo-line {
  width: 20px;
  height: 20px;
  background-size: cover;
  margin-right: 4px;

}

.double-height {
  height: fit-content;
  /* Adjust this value as needed */
}

.highlighted {
  background-color: #e0e0e0;
  /* Highlight color */
  border-radius: 5px;
}
</style>
