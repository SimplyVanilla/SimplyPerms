package it.fulminazzo.simplyperms.users;

import it.fulminazzo.yamlparser.parsers.CallableYAMLParser;

public class UserYAMLParser extends CallableYAMLParser<User> {

    public UserYAMLParser() {
        super(User.class, c -> new User(c.getName()));
    }
}
