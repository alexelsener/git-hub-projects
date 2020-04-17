<template>
  <v-card width="500" id="register" class="mx-auto mt-5 at-5">
    <v-card-title dark large class="elevation-2">Create a New Tournament</v-card-title>
    <v-divider></v-divider>
    <v-card-text id="instructions" class="text-center">
      Please fill out the information below and click the 'Create Your Tournament' button
      below to create your tournament.
    </v-card-text>
    <v-card-text>
      <v-form ref="createTournamentForm" v-model="isValid">
        <v-text-field
          label="Tournament Name"
          v-model="tournament.tournamentName"
          :rules="tNameRules"
          required
        ></v-text-field>
        <v-select :items="sports" v-model="tournament.game" :rules="sportSelectRules" required />
        <v-text-field
          type="date"
          label="Start Date"
          v-model="tournament.startDate"
          :rules="startDateRules"
          required
        ></v-text-field>
        <v-text-field
          type="date"
          label="End Date"
          v-model="tournament.endDate"
          :rules="endDateRules"
          required
        ></v-text-field>
        <v-text-field
          label="Location"
          v-model="tournament.location"
          :rules="locationRules"
          required
        ></v-text-field>
        <v-text-field
          label="Entry Fee"
          v-model="tournament.entryFee"
          :rules="entryFeeRules"
          required
        >
          <v-icon slot="prepend">mdi-currency-usd</v-icon>
        </v-text-field>
        <v-textarea
          label="Prize Description"
          v-model="tournament.prizeDescription"
          :rules="prizeDescriptionRules"
          required
        ></v-textarea>
      </v-form>
      <v-card-actions>
        <v-btn color="#03DAC5" @click="createTournament" :disabled="!isValid">Create Your Tournament</v-btn>
      </v-card-actions>
    </v-card-text>
  </v-card>
</template>

<script>
import auth from "@/auth";

export default {
  data() {
    return {
      isValid: true,
      tournament: {
        tournamentName: "",
        game: "",
        startDate: "",
        endDate: "",
        location: "",
        entryFee: "",
        prizeDescription: "",
        acceptingEntries: true
      },
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
      tNameRules: [
        v => !!v || "Tournament Name is required",
        v => (v && v.length <= 20) || " 20 characters or less please."
      ],
      sportSelectRules: [v => !!v || "Sport/Game selection is required."],
      startDateRules: [v => !!v || "required"],
      endDateRules: [v => !!v || "required"],
      locationRules: [
        v => !!v || "required",
        v => (v && v.length <= 30) || " 30 characters or less please."
      ],
      entryFeeRules: [
        v => !!v || "required",
        v => (v && v >= 5) || "A minimum cost of 5$ required for facility fees."
      ],
      prizeDescriptionRules: [v => !!v || "required"]
    };
  },
  methods: {
    createTournament() {
      if (this.$refs.createTournamentForm.validate()) {
        fetch(
          `${process.env.VUE_APP_REMOTE_API}/api/tournament?userId=${
            auth.getUser().id
          }`,
          {
            method: "POST",
            headers: {
              Authorization: "Bearer " + auth.getToken(),
              Accept: "application/json",
              "Content-Type": "application/json"
            },
            body: JSON.stringify(this.tournament)
          }
        ).then(response => {
          if (response.ok) {
            this.$router.push("/browse-tournaments");
          }
        });
      }
    }
  }
};
</script>

<style>
</style>