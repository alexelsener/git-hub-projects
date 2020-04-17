<template>
  <div>
    <v-card class="ml-3 mt-4" max-width="600">
      <v-list-item>
        <v-list-item-avatar tile size="150" ><v-img :src="getImage()"></v-img></v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title class="headline mb-1">{{user.firstName}} {{user.lastName}}</v-list-item-title>
          <div class="overline">Username: {{user.username}}</div>
          <div class="overline">Email: {{user.email}}</div>
          <v-card-actions>
            <edit-user :current-user="user" @update-user="updateUser()" />
          </v-card-actions>
        </v-list-item-content>
      </v-list-item>
    </v-card>
    <v-row class="mx-auto">
      <v-col>
        <h1>Teams</h1>
        <v-data-table :headers="teamHeaders" :items="teams" :items-per-page="5" >
          <template v-slot:item="row">
            <tr>
              <td
                class="clickable"
                @click="$router.push('/team-page/' + row.item.teamId)"
              ><v-btn small outlined block>{{row.item.teamName}}</v-btn> </td>
              <td>{{row.item.game}}</td>
              <td>{{row.item.teamBio}}</td>
              <td>
                <v-icon v-if="captainedTeams.includes(row.item.teamId)">mdi-check-bold</v-icon>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-col>
      <v-col>
        <h1>Tournaments</h1>
        <v-data-table
          :headers="tourneyHeaders"
          :items="tourneys"
          :items-per-page="5"
          class="elevation-1"
        >
          <template v-slot:item="row">
            <tr>
              <td
                class="clickable"
                @click="$router.push('/tournament-page/' + row.item.tournamentId)"
              ><v-btn small outlined block>{{row.item.tournamentName}}</v-btn></td>
              <td>{{row.item.game}}</td>
              <td>{{row.item.startDate}}</td>
            </tr>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import EditUser from "@/components/EditUser.vue";
import api from "@/api.js";
import auth from "@/auth.js";
import img from "@/image.js";

export default {
  components: {
    EditUser
  },
  data() {
    return {
      teamHeaders: [
        {
          text: "Teams",
          align: "start",
          value: "teamName",
          sortable: false
        },
        { text: "Game/Sport", value: "game", sortable: false },
        { text: "Team Bio", sortable: false, value: "teamBio" },
        { text: "Captain", sortable: false, value: "captain" }
      ],
      tourneyHeaders: [
        {
          text: "Tournament",
          align: "start",
          value: "name",
          sortable: false
        },
        { text: "Game/Sport", value: "game", sortable: false },
        { text: "Start Date", value: "start-date", sortable: false }
      ],
      user: null,
      teams: [
        {
          teamName: "",
          teamBio: "",
          game: ""
        }
      ],
      tourneys: [{
        tournamentName:"",
        game:"",
        startDate:""
      }],
      captainedTeams: []
    };
  },
  methods: {
    updateUser() {
      api.getUser().then(result => (this.user = result));
    },
    getImage(){
      return img.getImage(auth.getUser().id);
    }
  },
  created() {
    this.updateUser();

    api.getCaptainedTeams(auth.getUser().id).then(result => (this.captainedTeams = result));

    api.getUserTeams(auth.getUser().id).then(result => (this.teams = result));

    api.getUserTournaments(auth.getUser().id).then(result => (this.tourneys = result));
  }
};
</script>

<style>
</style>