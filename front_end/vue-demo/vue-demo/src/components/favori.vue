<template>
    <div class="container">
      <div class="glass-effect">
        <div class="title">favori</div>
        <div class="lieu-a">{{ depart }}</div>
        <div class="lieu-b">{{ arrivee }}</div>
      </div>
    </div>
  </template>
  
  <script setup>
  
  const depart = localStorage.getItem('currentDepart') || "Paris - Gare de Lyon";
  const arrivee = localStorage.getItem('currentArrivee') || "Paris - Gare du Nord";
   
  </script>
  
  <style scoped>
  .container {
    flex-direction: column;
    align-items: center;
    border-radius: 15px;  
    width: fit-content;
    z-index: 1;
    height: fit-content;
  
    margin: 0;
    justify-content: center;
    background: linear-gradient(135deg, rgba(255, 248, 40, 0.826), rgba(140, 155, 3, 0.832));
    border: 2px solid rgba(255, 255, 255, 1);
  
  
  }
  
  .glass-effect {
    border-radius: 15px;
    padding: 2rem;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(2px);
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 6px 20px rgba(0, 0, 0, 0.1);
    align-items: center;
    color: #fff;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
  }
  
  .lieu-a,
  .lieu-b {
    color: white;
    font-size: 1.1rem;
    text-align: left;
  }
  .title {
  font-size: 1.8rem;
  flex: 1;
}

  </style>
  