import Vue from 'vue'
import Router from 'vue-router'
import auth from './auth'
import Home from './views/Home.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import CreateTeam from './views/CreateTeam.vue'
import UserInfo from './views/UserInfo.vue'
import BrowseTeams from '@/views/BrowseTeams.vue'
import TeamPage from './views/TeamPage'
import BrowseTournaments from '@/views/BrowseTournaments.vue'
import CreateTournament from '@/views/CreateTournament.vue'
import TournamentPage from '@/views/TournamentPage.vue'
import TournamentPairings from '@/views/TournamentPairings.vue'
import Round from '@/views/Round.vue'
Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [{
    path: '/',
    name: 'home',
    component: Home,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/login",
    name: "login",
    component: Login,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/register",
    name: "register",
    component: Register,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/create-team",
    name: "create-team",
    component: CreateTeam,
    meta: {
      requiresAuth: false
    },
  }, {
    path: "/user-info",
    name: "user-info",
    component: UserInfo,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/browse-teams",
    name: "browse-teams",
    component: BrowseTeams,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/team-page/:id",
    name: "team-page",
    component: TeamPage,
    meta: {
      requiresAuth: false
    }

  },
  {
    path: "/browse-tournaments",
    name: "browse-tournaments",
    component: BrowseTournaments,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/create-tournament",
    name: "create-tournament",
    component: CreateTournament,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/tournament-page/:id",
    name: "tournament-page",
    component: TournamentPage,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/tournament-pairings/:id/:round",
    name: "tournament-pairings",
    component: TournamentPairings,
    meta: {
      requiresAuth: false
    }
  },
  {
    path: "/round/:id/:roundNumber",
    name: "round",
    component: Round,
    meta: {
      requiresAuth: false
    }
  }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
  const user = auth.getUser();

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && !user) {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;