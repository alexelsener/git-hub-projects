<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn text v-on="on">
          <v-icon small>mdi-cog</v-icon>Edit User
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Edit User Info</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-form v-model="isValid">
                <v-text-field v-model="user.firstName" label="First Name" :rules="userFirstNameRules" color="#03DAC5" required/>
                <v-text-field v-model="user.lastName" label="Last Name" :rules="userLastNameRules" color="#03DAC5" required/>
                <v-text-field v-model="user.email" label="Email" type="email" :rules="userEmailRules" color="#03DAC5" required/>
                </v-form>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red lighten-2" text @click="dialog = false; resetUser()">Cancel</v-btn>
          <v-btn color="#03DAC5" :disabled="!isValid" text @click="updateUser()">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import auth from '@/auth';
export default {
    props:{
        currentUser: Object
    },
  data() {
    return {
        user: {
            firstName:'',
            lastName:'',
            email:''
        },
        userFirstNameRules: [
          v => !!v || "first name is required.",
        ],
        userLastNameRules: [
          v => !!v || "last name is required.",
        ],
        userEmailRules: [
          v => !!v || "E-mail is required.",
          v => /.+@.+/.test(v) || 'E-mail must be valid'
        ],
        isValid: true,
        dialog: false
    };
  },
  methods:{
     resetUser(){
         this.user = Object.assign({},this.currentUser);
     },
     updateUser(){
       fetch(`${process.env.VUE_APP_REMOTE_API}/api/user`,{
         method:'PUT',
         headers:{
            Authorization: 'Bearer ' + auth.getToken(),
            Accept: "application/json",
           'content-type': 'application/json'
         },
         body: JSON.stringify(this.user)
       })
       .then(response =>{
         if(response.ok){
           this.$emit('update-user');
           this.dialog = false; 
         }
       })
     } 
  },
  created(){
      this.resetUser();
  }
  
};
</script>

<style>
</style>