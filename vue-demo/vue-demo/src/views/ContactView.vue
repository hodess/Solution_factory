<script setup>
import WelcomeItem from '../components/WelcomeItem.vue'
import CommunityIcon from '../components/icons/IconCommunity.vue'
import SupportIcon from '../components/icons/IconSupport.vue'
</script>

<template>
  <main>
    <div class="items">
      <WelcomeItem>
      <template #icon>
        <CommunityIcon />
      </template>
      <template #heading>Community</template>
      Join our community at <a>website.com/community</a> to get help, give help, and participate in community events.
    </WelcomeItem>
    
    <WelcomeItem>
      <template #icon>
        <SupportIcon />
      </template>
      <template #heading>Support</template>
      Get technical support by emailing us at <a>support@website.com</a> or phoning us at 514&nbsp;123&nbsp;4567.
    </WelcomeItem>
    </div>

    <div class="contact">
      <div>514 123 4567</div>
      <div>info@website.com</div>
      <div>
        9876 rue Saint-Denis<br />
        Montréal, Québec<br />
        A1B 2C3
      </div>
    </div>
  </main>
</template>

<style scoped>
main {
  display: flex;
  flex-flow: column nowrap;
  justify-content: flex-start;
  gap: 2rem;
}

.contact {
  margin-left: 3rem;
}

.contact > * {
  margin: 0.5rem 0rem;
}

@media (min-width: 1024px) {
  main {
    gap: 1rem;
  }

  .contact {
    margin-left: 6rem;
  }
}
</style>
