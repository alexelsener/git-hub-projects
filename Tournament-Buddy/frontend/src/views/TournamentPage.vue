 <template>
  <v-container>
    <v-row>
      <v-col cols="3">
        <v-img :src="getImage()" class="my-3" contain height="150" />
      </v-col>
      <v-col cols="3">
        <h1>{{tournament.tournamentName}}</h1>
        <span>Organizer: {{tournamentOwner.username}}</span>
        <p>Location: {{tournament.location}}</p>
      </v-col>
      <v-col cols="6">
        <v-row>
          <v-col>
            <h3>Tournament Game/Sport:</h3>
            <p>{{tournament.game}}</p>
          </v-col>
          <v-col>
            <h3>Tournament Prize Description:</h3>
            <p>{{tournament.prizeDescription}}</p>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            <h3>Tournament Entry Fee:</h3>
            <p>${{tournament.entryFee}}</p>
          </v-col>
          <v-col>
            <h3>Tournament Timeline:</h3>
            <span>From: {{tournament.startDate}}</span>
            <p>To: {{tournament.endDate}}</p>
          </v-col>
        </v-row>
      </v-col>
      <v-col v-if="tournament.tournamentOwner === currentUser">
        <v-card-actions>
          <accept-entries
            v-if="tournament.tournamentOwner === currentUser && !tournament.completed"
            :currentTourney="tournament"
          />
          <v-spacer></v-spacer>
          <v-btn
            color="#03DAC5"
            outlined
            v-if="tournament.tournamentOwner === currentUser  && !tournament.completed"
            :to="{ name: 'tournament-pairings', params:{id: tournament.tournamentId, round: rounds.length + 1}}"
          >Make New Round</v-btn>
          <v-spacer></v-spacer>
          <!--dialog pop up-->
          <!-- <v-btn
            color="#03DAC5"
            v-if="tournament.tournamentOwner === currentUser  && !tournament.completed"
            @click="endTournament()"
          >End Tournament</v-btn>-->
          <v-dialog v-model="dialog2" width="500" overlay-opacity="0.9">
            <template v-slot:activator="{ on }">
              <v-btn
                color="red lighten-2"
                outlined
                dark
                v-on="on"
                v-if="tournament.tournamentOwner === currentUser  && !tournament.completed"
              >End Tournament</v-btn>
            </template>
            <v-card>
              <v-card-title primary-title>Confirmation</v-card-title>
              <v-card-text>Are you sure you want to end the tournament?</v-card-text>
              <v-divider></v-divider>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="red"
                  text
                  @click="dialog2 = false; endTournament()"
                >Finalize Tournament</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <!--dialog pop up-->
          <v-spacer></v-spacer>
          <edit-tournament
            :current-tournament="tournament"
            @update-tournament="tourneyEvent()"
            v-if="tournament.tournamentOwner === currentUser  && !tournament.completed"
          />
        </v-card-actions>
      </v-col>
    </v-row>

    <v-row v-if="!tournament.completed">
      <v-col>
        <v-card>
          <v-card-title>
            Tournament Teams
            <v-spacer></v-spacer>
            <v-text-field
              v-model="searchTeams"
              append-icon="mdi-magnify"
              label="Search Tournament Teams"
              single-line
              hide-details
            ></v-text-field>
          </v-card-title>
          <v-data-table :headers="tourneyTeamHeaders" :items="tourneyTeams" :search="searchTeams">
            <template v-slot:item="row">
              <tr>
                <td
                  class="clickable"
                  @click="$router.push('/team-page/' + row.item.teamId)"
                >{{row.item.teamName}}</td>
                <td>{{row.item.captainUsername}}</td>
                <td>{{row.item.captainEmail}}</td>
                <td align="right" width="10">
                  <!--add dialog pop up-->
                  <v-dialog v-model="dialog" width="500" overlay-opacity="0.2">
                    <template v-slot:activator="{ on }">
                      <v-btn
                        small
                        color="red lighten-2"
                        outlined
                        dark
                        v-on="on"
                        v-if="tournament.tournamentOwner === currentUser && tournamentStarted === false"
                      >Remove Team</v-btn>
                    </template>
                    <v-card>
                      <v-card-title primary-title>Confirmation</v-card-title>
                      <v-card-text>Are you sure you want to remove this team?</v-card-text>
                      <v-divider></v-divider>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="red"
                          text
                          @click="dialog = false; deleteTeamFromTournament(row.item.teamId)"
                        >Remove Team</v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                  <!-- end dialog-->
                </td>
              </tr>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
    <h1 class="text-center" v-if="tournament.completed">Final Results</h1>
    <v-row v-if="tournament.completed">
      <v-spacer></v-spacer>
      <v-col col="1" class="text-center d-flex align-end silver">
        <v-card height="200" width="150">
          <h2>{{top3[2]}}</h2>
          <v-icon large color="#E0E0E0">mdi-medal</v-icon>
        </v-card>
      </v-col>
      <v-spacer></v-spacer>
      <v-col col="1" class="text-center d-flex align-end">
        <v-card height="300" width="150" color="">
          <h1>{{top3[1]}}</h1>
          <v-icon large color="#FFD600">mdi-medal</v-icon>
        </v-card>
      </v-col>
      <v-spacer></v-spacer>
      <v-col col="1" class="text-center d-flex align-end">
        <v-card height="100" width="150" color="">
          <h3>{{top3[3]}}</h3>
          <v-icon large color="#cd7f32">mdi-medal</v-icon>
        </v-card>
      </v-col>
      <v-spacer></v-spacer>
    </v-row>
    <v-row>
      <v-col class="d-flex" cols="12">
        <v-spacer></v-spacer>
        <h3 v-if="rounds.length > 0">Rounds:</h3>
        <v-card class="round" v-for="currentRound in rounds" :key="currentRound">
          <v-card-actions>
            <v-btn
              :to="{name: 'round', params: {id : tournament.tournamentId, roundNumber : currentRound}}"
              color="#03DAC5"
              small
              outlined
              fab
            >{{currentRound}}</v-btn>
          </v-card-actions>
        </v-card>
        <v-spacer></v-spacer>
      </v-col>
    </v-row>
  </v-container>
</template>
 
 <script>
import auth from "@/auth";
import EditTournament from "@/components/EditTournament.vue";
import AcceptEntries from "@/components/AcceptEntries.vue";
import img from '@/image.js';

export default {
  components: {
    EditTournament,
    AcceptEntries
  },
  props: {
    currentTournament: Object
  },
  data() {
    return {
      startingTournament: {
        tournamentId: "",
        tournamentName: "",
        entryFee: "",
        game: "",
        location: "",
        prizeDescription: "",
        startDate: "",
        endDate: ""
      },
      dialog: false,
      dialog2: false,
      currentUser: null,
      searchTeams: "",
      tourneyTeamHeaders: [
        { text: "Team Name", value: "teamName" },
        { text: "Captain", value: "username" },
        { text: "Captain Email", value: "email" },
        { text: "", value: "button" }
      ],
      tourneyTeams: [],
      tournamentOwner: {
        username: ""
      },
      rounds: [],
      top3: []
    };
  },
  methods: {
    getTournament() {
      const tourneyId = this.$route.params.id;
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/${tourneyId}`, {
        method: "GET",
        headers: {
          Authorization: "Bearer " + auth.getToken()
        }
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(data => {
          this.startingTournament = data;
        });
    },
    getTournamentOwnerUsername() {
      const tourneyId = this.$route.params.id;
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/user/tournamentOwner/${tourneyId}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken()
          }
        }
      )
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(data => (this.tournamentOwner = data));
    },
    getTourneyTeams() {
      const tourneyId = this.$route.params.id;
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/team/tournament/${tourneyId}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken()
          }
        }
      )
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(teamsInfo => (this.tourneyTeams = teamsInfo));
    },
    deleteTeamFromTournament(teamId) {
      const tournamentId = this.$route.params.id;
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/team/tournament/${tournamentId}?teamId=${teamId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: "Bearer " + auth.getToken()
          }
        }
      ).then(response => {
        if (response.ok) {
          return response.json();
        }
      });
      this.getTourneyTeams();
    },
    getTourneyRounds() {
      const tournamentId = this.$route.params.id;
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/tournament/tournamentRounds/${tournamentId}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken()
          }
        }
      )
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(data => (this.rounds = data));
    },
    endTournament() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/tournament/end?tourneyId=${this.tournament.tournamentId}`,
        {
          method: "PUT",
          headers: {
            Authorization: "Bearer " + auth.getToken(),
            Accept: "application/json",
            "content-type": "application/json"
          }
        }
      ).then(response => {
        if (response.ok) {
          this.getTourneyRounds();
          this.currentUser = auth.getUser().id;
          this.getTournamentOwnerUsername();
          this.getTourneyTeams();
          this.getTournament();
          this.$emit("update-tournament", this.tournament.tournamentId);
        }
      });
    },
    getTop3() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/tournament/wins?tourneyId=${this.$route.params.id}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken()
          }
        }
      )
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(data => (this.top3 = data));
    },
    updateTourney() {
      this.getTourneyRounds();
      this.getTourneyTeams();
      this.currentUser = auth.getUser().id;
      this.getTournamentOwnerUsername();
      this.getTournament();
      this.getTop3();
    },
    tourneyEvent() {
      this.$emit("update-tournament", this.tournament.tournamentId);
    },
    getImage(){
      return img.getImage(this.tournament.tournamentId);
    }
  },
  computed: {
    tournamentStarted() {
      var date1 = this.tournament.startDate;
      date1 = new Date(date1);
      var date2 = new Date();
      return date1 <= date2;
    },
    tournament() {
      if (this.currentTournament !== null) {
        this.updateTourney();
        return this.currentTournament;
      } else {
        return this.startingTournament;
      }
    }
  },
  created() {
    this.updateTourney();
  }
};
</script>
 
 <style scoped>
.round {
  margin-right: 10px;
  margin-left: 10px;
}
</style>