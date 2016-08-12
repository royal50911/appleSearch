package com.example.denniskim.crisp.UI.Login_SignUp;

/**
 * Created by denniskim on 10/26/15.
 */
public class User {

        private int _id;
        private String username;
        private String password;

        public User(){

        }

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public int get_id() {
            return _id;
        }

        public String getUsername() {
            return username;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public void setUsername(String product_name) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
}
