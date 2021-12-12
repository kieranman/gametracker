import React from 'react';
import AuthService from "../services/AuthService";


export default class Login extends React.Component {
    constructor(props) {
      super(props);
      this.handleLogin = this.handleLogin.bind(this);
      this.onChangeUsername = this.onChangeUsername.bind(this);
      this.onChangePassword = this.onChangePassword.bind(this);
  
      this.state = {
        username: "",
        password: "",
        loading: false,
        message: ""
      };
    }
  
    onChangeUsername(e) {
      this.setState({
        username: e.target.value
      });
    }
  
    onChangePassword(e) {
      this.setState({
        password: e.target.value
      });
    }
  
    handleLogin(e) {
      e.preventDefault();
  
      this.setState({
        message: "",
        loading: true
      });
  

        AuthService.login(this.state.username, this.state.password).then(
          () => {
            this.props.history.push("/list");
            window.location.reload();
          
        });
      }
    
    
  
    render() {
      return (
        <div className ="login-form">
            <form onSubmit={this.handleLogin}>

            <label for="username">Username</label>
            <input type="text" id="username" name="username" placeholder="Enter Username.."
            value ={this.state.username} onChange={this.onChangeUsername} />
            <label for="password">Password</label>
            <input type="text" id="password" name="password" placeholder="Enter Password.."
            value ={this.state.password} onChange={this.onChangePassword}/>
                <input  type="submit" value="Submit"/>
                <input type="reset" value="Reset"/>
            </form>
        </div>




  
          

      );
    }
  }
  