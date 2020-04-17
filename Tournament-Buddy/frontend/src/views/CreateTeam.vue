<template>
  <v-app>
    <v-card width="500" class="mx-auto mt-5">
      <v-card-title>
        <h1 class="display-1">Create A Team</h1>
      </v-card-title>
      <v-card-text>
        <v-form ref="createTeamForm" v-model="isValid">
          <v-text-field label="Team Name" :rules="teamNameRules" required v-model="team.teamName" />
          <v-spacer></v-spacer>
          <h4>Please select your sport/game:</h4>

          <v-select :items="sports" v-model="team.game" :rules="sportSelectRules" required />

          <h4>Accepting New Members?</h4>

          <v-radio-group
            v-model="acceptingMembersInput"
            :rules="acceptingMembersRules"
            required
            row
          >
            <v-radio color="#03DAC5" label="yes" value="true"></v-radio>
            <v-radio color="#BB86FC" label="no" value="false"></v-radio>
          </v-radio-group>

          <v-text-field
            v-model="team.teamBio"
            label="About The Team:"
            :rules="teamBioRules"
            required
          />
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn color="#03DAC5" @click="createTeam" :disabled="!isValid" >Register</v-btn>
      </v-card-actions>
    </v-card>
  </v-app>
</template>

<script>
import auth from "@/auth";

export default {
  name: "registerT",

  components: {},

  data: () => {
    return {
      checkbox1: true,
      isValid:true,
      sports: [
        "Volleyball",
        "Basketball",
        "Soccer",
        "Hockey",
        "Bike Polo",
        "Euchre",
        "Poker",
        "DnD",
        "Magic The Gathering",
        "Super Smash Brothers",
        "Other"
      ],
      team: {
        teamName: "",
        game: "",
        acceptingMembers: "",
        teamBio: ""
      },
      registrationErrors: false,
      acceptingMembersInput: "",
      acceptingMembersRules: [v => !!v || "must select"],
      teamNameRules: [v => !!v || "Team Name is required"],
      teamBioRules: [v => !!v || "Some team info is required."],
      sportSelectRules: [v => !!v || "Sport/Game selection is required."]
    };
  },
  
  methods: {
    createTeam() {
      if (this.$refs.createTeamForm.validate()) {
        this.team.acceptingMembers = this.acceptingMembersInput === "true";
        fetch(
          `${process.env.VUE_APP_REMOTE_API}/api/team?userId=${
            auth.getUser().id
          }`,
          {
            method: "POST",
            headers: {
              Authorization: "Bearer " + auth.getToken(),
              Accept: "application/json",
              "Content-Type": "application/json"
            },
            body: JSON.stringify(this.team)
          }
        ).then(response => {
          if (response.ok) {
            this.$router.push("/user-info");
          }
        });
      }
    }
  }
};
</script>