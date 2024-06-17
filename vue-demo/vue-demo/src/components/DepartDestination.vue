<script setup>
  import { ref, computed } from 'vue';
  import { stations } from "@/stockage/gares";


  //Pour la navigation avec boutons
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




  const { searchText: searchTextDepart, searchResults: searchResultsDepart } = useSearch(stations);
  const { searchText: searchTextArrivee, searchResults: searchResultsArrivee } = useSearch(stations);

  const isDepartFocused = ref(false);
  const isArriveeFocused = ref(false);

  function useSearch(stations) {
    const searchText = ref('');
    const searchResults = computed(() => {
      if (!searchText.value) {
        return [];
      }
      return stations.filter(station =>
          station.name.toLowerCase().includes(searchText.value.toLowerCase()) ||
          station.address.toLowerCase().includes(searchText.value.toLowerCase())
      ).slice(0, 4);
    });

    return {
      searchText,
      searchResults,
    };
  }

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
  }

  function getStationLines(lines) {
    const lineMap = {
      'M1': 'src/components/lines/metro-1.png',
      'M2': 'src/components/lines/metro-2.png',
      'M3': 'src/components/lines/metro-3.png',
      'M4': 'src/components/lines/metro-4.png',
      'M5': 'src/components/lines/metro-5.png',
      'M6': 'src/components/lines/metro-6.png',
      'M7': 'src/components/lines/metro-7.png',
      'M8': 'src/components/lines/metro-8.png',
      'M9': 'src/components/lines/metro-9.png',
      'M10': 'src/components/lines/metro-10.png',
      'M11': 'src/components/lines/metro-11.png',
      'M12': 'src/components/lines/metro-12.png',
      'M13': 'src/components/lines/metro-13.png',
      'M14': 'src/components/lines/metro-14.png',
      'RER A': 'src/components/lines/rer-a.png',
      'RER B': 'src/components/lines/rer-b.png',
      'RER C': 'src/components/lines/rer-c.png',
      'RER D': 'src/components/lines/rer-d.png',
      'RER E': 'src/components/lines/rer-e.png'
    };

    const metroLines = [];
    const rerLines = [];
    lines.split(', ').map(l => l.trim()).forEach(l => {
      if (lineMap[l]) {
        if (l.startsWith('M')) {
          metroLines.push(lineMap[l]);
        } else if (l.startsWith('RER')) {
          rerLines.push(lineMap[l]);
        }
      } else {
        console.log(`Image not found for line: ${l}`);
      }
    });

    return {
      metroLines,
      rerLines
    };
  }


</script>

<template>
  <div class="block_input">
    <div class="title-trip">Où voulez-vous aller ?</div>

    <!-- Departure input -->
    <div class="small-title">De :</div>
    <input
        v-model="searchTextDepart"
        @focus="isDepartFocused = true"
        @blur="() => setTimeout(() => isDepartFocused = false, 100)"
        @keydown.down.prevent="navigateResults('depart', 1)"
        @keydown.up.prevent="navigateResults('depart', -1)"
        @keydown.enter.prevent="selectHighlightedResult('depart')"
        type="search"
        class="search"
        placeholder="Gare de départ"
    />

    <ul v-if="isDepartFocused && searchResultsDepart.length">
      <li
          v-for="(result, index) in searchResultsDepart"
          :key="result.name"
          @click="selectStation(result, 'depart')"
          :class="{ 'double-height': getStationLines(result.line).metroLines.length && getStationLines(result.line).rerLines.length, 'highlighted': index === selectedDepartIndex }"
      >
        <strong>{{ result.name }}</strong><br>
        <div class="lines-container">
          <div class="metro-lines" v-if="getStationLines(result.line).metroLines.length">
            <template v-for="image in getStationLines(result.line).metroLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
          <div class="rer-lines" v-if="getStationLines(result.line).rerLines.length">
            <template v-for="image in getStationLines(result.line).rerLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
        </div>
      </li>
    </ul>


    <!-- Arrival input -->
    <div class="small-title">À :</div>
    <input
        v-model="searchTextArrivee"
        @focus="isArriveeFocused = true"
        @blur="() => setTimeout(() => isArriveeFocused = false, 100)"
        @keydown.down.prevent="navigateResults('arrivee', 1)"
        @keydown.up.prevent="navigateResults('arrivee', -1)"
        @keydown.enter.prevent="selectHighlightedResult('arrivee')"
        type="search"
        class="search"
        placeholder="Gare d'arrivée"
    />
    <ul v-if="isArriveeFocused && searchResultsArrivee.length">
      <li
          v-for="(result, index) in searchResultsArrivee"
          :key="result.name"
          @click="selectStation(result, 'arrivee')"
          :class="{ 'double-height': getStationLines(result.line).metroLines.length && getStationLines(result.line).rerLines.length, 'highlighted': index === selectedArriveeIndex }"
      >
        <strong>{{ result.name }}</strong><br>
        <div class="lines-container">
          <div class="metro-lines" v-if="getStationLines(result.line).metroLines.length">
            <template v-for="image in getStationLines(result.line).metroLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
          <div class="rer-lines" v-if="getStationLines(result.line).rerLines.length">
            <template v-for="image in getStationLines(result.line).rerLines">
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

strong{
  min-width: 50%;
}

.logo-line {
  background-size: cover;
  background-repeat: no-repeat;
  width: 1.25rem;
  height: 1.25rem;
  margin-top: 3px;
  margin-right: 3px;
  margin-bottom: 3px;
}


.lines-container {
  display: flex;
  flex-direction: column;
}

.metro-lines,
.rer-lines {
  display: flex;
}



.double-height {
  height: 4rem; /* Adjust this value as needed */
}


<style scoped>
 .lines-container {
   display: flex;
   flex-direction: column;
 }

.metro-lines,
.rer-lines {
  display: flex;
}

.logo-line {
  width: 24px;
  height: 24px;
  background-size: cover;
  margin-right: 4px;
}

.double-height {
  height: 80px; /* Adjust this value as needed */
}

.highlighted {
  background-color: #e0e0e0; /* Highlight color */
  border-radius: 5px;
}
</style>



