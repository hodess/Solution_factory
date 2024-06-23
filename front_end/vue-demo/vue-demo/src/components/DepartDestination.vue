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
      // Metro lines
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
      // RER lines
      'RER A': 'src/components/lines/rer-a.png',
      'RER B': 'src/components/lines/rer-b.png',
      'RER C': 'src/components/lines/rer-c.png',
      'RER D': 'src/components/lines/rer-d.png',
      'RER E': 'src/components/lines/rer-e.png',
      // Tram lines
      'Tram 1': 'src/components/lines/tram-1.png',
      'Tram 2': 'src/components/lines/tram-2.png',
      'Tram 3a': 'src/components/lines/tram-3a.png',
      'Tram 3b': 'src/components/lines/tram-3b.png',
      'Tram 4': 'src/components/lines/tram-4.png',
      'Tram 5': 'src/components/lines/tram-5.png',
      'Tram 6': 'src/components/lines/tram-6.png',
      'Tram 7': 'src/components/lines/tram-7.png',
      'Tram 8': 'src/components/lines/tram-8.png',
      'Tram 9': 'src/components/lines/tram-9.png',
      'Tram 10': 'src/components/lines/tram-10.png',
      'Tram 11': 'src/components/lines/tram-11.png',
      'Tram 12': 'src/components/lines/tram-12.png',
      'Tram 13': 'src/components/lines/tram-13.png',
      'Tram 14': 'src/components/lines/tram-14.png',
      // Transilien lines
      'transilien H': 'src/components/lines/transilien-h.png',
      'transilien J': 'src/components/lines/transilien-j.png',
      'transilien K': 'src/components/lines/transilien-k.png',
      'transilien L': 'src/components/lines/transilien-l.png',
      'transilien N': 'src/components/lines/transilien-n.png',
      'transilien P': 'src/components/lines/transilien-p.png',
      'transilien R': 'src/components/lines/transilien-r.png',
      'transilien U': 'src/components/lines/transilien-u.png',
      'transilien V': 'src/components/lines/transilien-v.png',
      //logo
      'Logo Metro' : 'src/components/lines/logo-metro.png',
      'Logo RER' : 'src/components/lines/logo-RER.png',
      'Logo tram' : 'src/components/lines/logo-tram.png',
      'Logo transilien' : 'src/components/lines/logo-transilien.png'
    };

    const metroLines = [];
    const rerLines = [];
    const tramLines = [];
    const transilienLines = [];

    lines.split(', ').forEach(line => {
      const formattedLine = line.trim();
      if (lineMap[formattedLine]) {
        if (formattedLine.startsWith('M')) {
          metroLines.push(lineMap[formattedLine]);
        } else if (formattedLine.startsWith('RER')) {
          rerLines.push(lineMap[formattedLine]);
        } else if (formattedLine.startsWith('Tram')) {
          tramLines.push(lineMap[formattedLine]);
        } else if (formattedLine.startsWith('transilien')) {
          transilienLines.push(lineMap[formattedLine]);
        }
      } else {
        console.log(`Image not found for line: ${line}`);
      }
    });

    return {
      metroLines,
      rerLines,
      tramLines,
      transilienLines
    };
  }
</script>

<template>
  <div class="block_input">

    <!-- Departure input -->
    <!-- <div class="small-title">De :</div> -->
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
          :class="{ 'double-height': getStationLines(result.line).metroLines.length && getStationLines(result.line).rerLines.length && getStationLines(result.line).tramLines.length && getStationLines(result.line).transilienLines.length, 'highlighted': index === selectedDepartIndex }"
      >
        <strong>{{ result.name }}</strong><br>
        <div class="lines-container">
          <div class="metro-lines" v-if="getStationLines(result.line).metroLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-metro.png) `}"></div>
            <template v-for="line in getStationLines(result.line).metroLines">
              <div v-if="line.type === 'logo'" class="logo-line" :style="{ backgroundImage: `url(${line.image})` }"></div>
              <div v-else class="logo-line" :style="{ backgroundImage: `url(${line})` }"></div>
            </template>
          </div>
          <div class="rer-lines" v-if="getStationLines(result.line).rerLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-RER.png) `}"></div>
            <template v-for="line in getStationLines(result.line).rerLines">
              <div v-if="line.type === 'logo'" class="logo-line" :style="{ backgroundImage: `url(${line.image})` }"></div>
              <div v-else class="logo-line" :style="{ backgroundImage: `url(${line})` }"></div>
            </template>
          </div>
          <div class="tram-lines" v-if="getStationLines(result.line).tramLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-tram.png) `}"></div>
            <template v-for="line in getStationLines(result.line).tramLines">
              <div v-if="line.type === 'logo'" class="logo-line" :style="{ backgroundImage: `url(${line.image})` }"></div>
              <div v-else class="logo-line" :style="{ backgroundImage: `url(${line})` }"></div>
            </template>
          </div>
          <div class="transilien-lines" v-if="getStationLines(result.line).transilienLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-transilien.png) `}"></div>
            <template v-for="line in getStationLines(result.line).transilienLines">
              <div v-if="line.type === 'logo'" class="logo-line" :style="{ backgroundImage: `url(${line.image})` }"></div>
              <div v-else class="logo-line" :style="{ backgroundImage: `url(${line})` }"></div>
            </template>
          </div>

        </div>
      </li>
    </ul>

    <!-- Arrival input -->
    <!-- <div class="small-title">À :</div> -->
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
          :class="{ 'double-height': getStationLines(result.line).metroLines.length && getStationLines(result.line).rerLines.length && getStationLines(result.line).tramLines.length && getStationLines(result.line).transilienLines.length, 'highlighted': index === selectedArriveeIndex }"
      >
        <strong>{{ result.name }}</strong><br>
        <div class="lines-container">
          <div class="metro-lines" v-if="getStationLines(result.line).metroLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-metro.png) `}"></div>
            <template v-for="image in getStationLines(result.line).metroLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
          <div class="rer-lines" v-if="getStationLines(result.line).rerLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-RER.png) `}"></div>
            <template v-for="image in getStationLines(result.line).rerLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
          <div class="tram-lines" v-if="getStationLines(result.line).tramLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-tram.png) `}"></div>
            <template v-for="image in getStationLines(result.line).tramLines">
              <div class="logo-line" :style="{ backgroundImage: `url(${image})` }"></div>
            </template>
          </div>
          <div class="transilien-lines" v-if="getStationLines(result.line).transilienLines.length">
            <div class="logo-line" :style="{ backgroundImage: `url(src/components/lines/logo-transilien.png) `}"></div>
            <template v-for="image in getStationLines(result.line).transilienLines">
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

strong{
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
  height: 4rem; /* Adjust this value as needed */
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
  height: fit-content; /* Adjust this value as needed */
}

.highlighted {
  background-color: #e0e0e0; /* Highlight color */
  border-radius: 5px;
}
</style>



