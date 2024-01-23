const app = Vue.createApp({

    data() {
        return {
            currentcategory: null,
            currentmeal: null
        }
    },

    methods: {

    },

    watch: {
        currentcategory() { // hide and show meal categories after selecting category
            document.querySelectorAll('.mealcategory').forEach((el) => {
              if (el.id != this.currentcategory) {
                el.style.display = 'none'
              } else {
                el.style.display = ''
              }
            });
          }
    }
})

app.mount("#AddMeal")