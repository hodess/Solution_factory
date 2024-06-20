<script setup>
</script>

<template>
  <header class="navbar">
    <div class="logo-locomotive"/>
    <div class="title-locomotive">Locomotive</div>
    <div class="bloc-right"/>
  </header>

</template>

<style>
.navbar {
  background-color: var(--vt-c-navbar-color);
  color: var(--vt-c-white);
  padding: 1rem;
  left: 0;
  top: 3.5vh;
  transform: translateY(-50%);
  align-items: center;
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  position: fixed;
  display: flex;
  width: 100%;
  box-shadow: 0 2px 4px var( --vt-c-black);
  z-index: 3;
}
.logo-locomotive{
  background-image: url("@/assets/logo-locomotive.png");
  background-size: cover; /* Pour ajuster la taille de l'image à la fenêtre */
  background-position: center;
  z-index: -0.5;
  border-radius: 15px;
  height: 5rem;
  width: 5rem;
  margin-top: 3rem
}
.bloc-right{
  height: 5rem;
  width: 5rem;
  margin-top: 3rem
}

.title-locomotive {
  margin-top: 3rem;
  font-size: 1.8rem;
  flex: 1;
}


.title-trip{
  color: #f8f8f8;
  font-size: 1.2rem;
}
</style>