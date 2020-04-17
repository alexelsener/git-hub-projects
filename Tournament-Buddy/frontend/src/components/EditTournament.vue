<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn outlined color="	#008080" dark v-on="on">
          <v-icon small>mdi-cog</v-icon>Edit Tournament
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Edit Tournament Info</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-form v-model="isValid">
                  <v-text-field
                    color="#03DAC5"
                    v-model="tournament.tournamentName"
                    label="Tournament Name"
                    :rules="tourneyNameRules"
                  />
                  <v-text-field
                    color="#03DAC5"
                    v-model="tournament.entryFee"
                    label="Entry Fee"
                    :rules="entryFeeRules"
                  />
                  <v-text-field
                    color="#03DAC5"
                    v-model="tournament.prizeDescription"
                    label="Prize Description"
                    :rules="prizeDescriptionRules"
                  />
                  <v-text-field
                    color="#03DAC5"
                    v-model="tournament.location"
                    label="Location"
                    :rules="locationRules"
                  ></v-text-field>
                  <v-text-field
                    color="#03DAC5"
                    v-model="tournament.startDate"
                    label="Tournament Start Date"
                    :rules="startDateRules"
                  />
                  <v-text-field
                    color="#03DAC5"
                    v-model="tournament.endDate"
                    label="Tournament End Date"
                    :rules="endDateRules"
                  />
                </v-form>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red lighten-2" text @click="dialog = false; resetTournament()">Close</v-btn>
          <v-btn color="#03DAC5" text :disabled="!isValid" @click="updateTournament()">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import auth from "@/auth";
export default {
  props: {
    currentTournament: Object
  },
  data() {
    return {
      tournament: {
        tournamentId: "",
        tournamentName: "",
        entryFee: "",
        game: "",
        location: "",
        prizeDescription: "",
        startDate: "",
        endDate: ""
      },
      tourneyNameRules: [v => !!v || "Tournament Name is required."],
      entryFeeRules: [
        v => !!v || "Entry Fee is required.",
        v => v >= 5 || "Entry Fee must be a number greater than or equal to 5."
      ],
      prizeDescriptionRules: [v => !!v || "Prize Description is required."],
      locationRules: [v => !!v || "Location is required."],
      startDateRules: [
        v => !!v || "A Start Date is required for your tournament."
      ],
      endDateRules: [
        v => !!v || "An End Date is required for your tournament."
      ],
      isValid: true,
      dialog: false
    };
  },
  methods: {
    resetTournament() {
      this.tournament = Object.assign({}, this.currentTournament);
    },
    updateTournament() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament`, {
        method: "PUT",
        headers: {
          Authorization: "Bearer " + auth.getToken(),
          Accept: "application/json",
          "content-type": "application/json"
        },
        body: JSON.stringify(this.tournament)
      }).then(response => {
        if (response.ok) {
          this.$emit("update-tournament");
          this.dialog = false;
        }
      });
    }
  },
  created() {
    this.resetTournament();
  }
};
</script>

<style>
</style>