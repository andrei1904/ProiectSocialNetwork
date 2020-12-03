package socialnetwork.repository.file;

import socialnetwork.domain.Entity;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.memory.InMemoryRepository;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


///Aceasta clasa implementeaza sablonul de proiectare Template Method; puteti inlucui solutia propusa cu un Factori (vezi mai jos)
public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    String fileName;

    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(linie -> {
                E entity = extractEntity(Arrays.asList(linie.split(";")));
                super.save(entity);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * extract entity  - template method design pattern
     * creates an entity of type E having a specified list of @code attributes
     *
     * @param attributes k
     * @return an entity of type E
     */
    public abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    protected abstract String createEntityAsString(E entity);

    @Override
    public Optional<E> save(E entity) {
        Optional<E> e = super.save(entity);
        if (e.isPresent()) { // daca nu exista
            writeToFile(entity);
            return e;
        }
        return Optional.empty(); // daca nu exista
    }


    @Override
    public Optional<E> delete(ID id) {
        Optional<E> e = super.delete(id);
        if (e.isPresent()) { // s a gasit entitatea
            deleteAllFromFile();
            for (E entity : super.findAll()) {
                writeToFile(entity);
            }
        }
        return e;
    }

    @Override
    public Optional<E> update(E entity) {
        Optional<E> e= super.update(entity);

        if (e.isPresent()) // nu exista
            return Optional.empty();
        else {
            deleteAllFromFile();
            for (E en : super.findAll()) {
                writeToFile(en);
            }
        }
        return e;
    }

    protected void writeToFile(E entity) {
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName, true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void deleteAllFromFile() {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

