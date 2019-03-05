package org.lpro.sandwichservice.boundary;

import org.lpro.sandwichservice.Exception.NotFound;
import org.lpro.sandwichservice.entity.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class ItemRepresentation {

    private ItemResource ir;
    private CommandeResource cr;

    public ItemRepresentation(ItemResource ir, CommandeResource cr) {
        this.ir = ir;
        this.cr = cr;
    }

    @GetMapping("/commandes/{id_commande}/items")
    public ResponseEntity<?> getItemByCommandeId(@PathVariable("id_commande") String id_commande) throws NotFound {
        if (!cr.existsById(id_commande)){
            throw new NotFound("Commande inexistante");
        }
        return new ResponseEntity<>(cr.findById(id_commande).get().getItems(), HttpStatus.OK);
    }

    @PostMapping("/commandes/{id_commande}/items")
    public ResponseEntity<?> addItem(@PathVariable("id_commande") String id_commande, @RequestBody Item item) throws NotFound {
        return cr.findById(id_commande)
                .map(commande -> {
                    item.setId(UUID.randomUUID().toString());
                    item.setCommande(commande);
                    ir.save(item);
                    return new ResponseEntity<>(HttpStatus.CREATED);
                }).orElseThrow( () -> new NotFound("Commande inexistante"));
    }

    @PutMapping("/commandes/{id_commande}/items/{id_item}")
    public ResponseEntity<?> putItem(@PathVariable("id_commande") String id_commande,@PathVariable("id_item") String id_item, @RequestBody Item itemUpdated) throws NotFound {
        if (!cr.existsById(id_commande)){
            throw new NotFound("Intervenant inexistant");
        }
        return ir.findById(id_item).map(item -> {
            item.setLibelle(itemUpdated.getLibelle());
            item.setQuantite(itemUpdated.getQuantite());
            item.setTarif(itemUpdated.getTarif());
            item.setUri(itemUpdated.getUri());
            ir.save(item);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseThrow( () -> new NotFound("Item inexistant"));
    }

    @DeleteMapping("/commandes/{id_commande}/items/{id_item}")
    public ResponseEntity<?> deleteItem(@PathVariable("id_commande") String id_commande,@PathVariable("id_item") String id_item) throws NotFound {
        if (!cr.existsById(id_commande)){
            throw new NotFound("Commande inexistante");
        }
        return ir.findById(id_item).map(item -> {
            ir.delete(item);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }).orElseThrow( () -> new NotFound("Item inexistant"));
    }
}