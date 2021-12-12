import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import Navigation from './components/Navigation';
import Welcome from './components/Welcome';
import Footer from './components/Footer';
import Game from './components/Game';
import GameList from './components/GameList';
import { BrowserRouter as Router , Route, Switch} from 'react-router-dom';
import Login from './components/Login';
import SignUp from './components/SignUp';
import GameDetails from './components/GameDetails';
import AddToList from './components/AddToList';

function App() {

























  
  const marginTop ={
    marginTop:"30px"
  
  };
 
  return (
    <div className="background">
    <Router>
      <Navigation/>
    <div className="App" style ={marginTop}>
        
          
       
      <Switch>
        <Route path='/' exact component = {Welcome} />
        <Route path='/add' exact component = {Game}/>
        <Route path='/edit/:id' exact component = {Game}/>
        <Route path='/list' exact component = {GameList}/>
        <Route path='/login' exact component = {Login}/>
        <Route path='/signup' exact component = {SignUp}/>
        <Route path='/gameDetails/:id' exact component = {GameDetails}/>
        <Route path='/addToList/:id' exact component = {AddToList}/>

        

      </Switch>
 
      <Footer/>
      
    </div>
    </Router>
    </div>
  );
}

export default App;
