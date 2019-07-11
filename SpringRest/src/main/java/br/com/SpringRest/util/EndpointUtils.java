package br.com.SpringRest.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class EndpointUtils implements Serializable {

    public ResponseEntity<?> returnObjectOrNotFound(Object object) {
        return object == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(object, HttpStatus.OK);
    }

    public ResponseEntity<?> returnObjectOrNotFound(List<?> list) {
        return list == null || list.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(list, HttpStatus.OK);
    }

}
