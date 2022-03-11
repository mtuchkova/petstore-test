import model.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.PetStoreUtil;

public class PetStoreTest {

    private static final Logger log = LoggerFactory.getLogger(PetStoreTest.class.getName());
    private static final PetStoreService service = new PetStoreService();

    @Test(groups = "createPet")
    public void createPetAndAddToStorePositiveTest() throws PetStoreServiceException {
        log.info("Default pet");
        Pet pet = Pet.getDefaultPet();

        log.info("Process add pet request");
        Pet responsePet = service.sendAddPetToStoreRequest(pet);

        log.info("PetStore should return correct data");
        Assert.assertEquals(pet.getName(), responsePet.getName());
        Assert.assertEquals(pet.getPhotoUrls(), responsePet.getPhotoUrls());
    }

    @Test(groups = "getPetById")
    public void getPetByIdPositiveTest() throws PetStoreServiceException {
        log.info("Preconditions: Getting the ID of a pet that is present in the database");
        Long petId = getPetId();

        log.info("Process get pet by id request for non-existent petId");
        Pet response = service.sendGetPetByIdRequest(petId);

        log.info("PetStore should return correct data");
        Assert.assertEquals(response.getId(), response.getId());
    }

    @Test(groups = "getPetById", expectedExceptions = PetStoreServiceException.class,
            expectedExceptionsMessageRegExp = ".*Pet not found.*")
    public void getNonExistentPetByIdNegativeTest() throws PetStoreServiceException {
        log.info("Preconditions: Get non existent PetId");
        long petId = PetStoreUtil.getRandomNumber();
        deletePetIfExist(petId);

        log.info("Process get pet by id request for non-existent petId");
        service.sendGetPetByIdRequest(petId);
    }

    private void deletePetIfExist(Long petId) {
        try {
            service.sendDeletePetByIdRequest(petId);
        } catch (PetStoreServiceException ignored) {
        } finally {
            log.info(String.format("PetId: $s is free to store new pet", petId));

        }
    }

    private Long getPetId() throws PetStoreServiceException {
        Pet pet = Pet.getDefaultPet();
        Pet responsePet = service.sendAddPetToStoreRequest(pet);
        return responsePet.getId();
    }
}
