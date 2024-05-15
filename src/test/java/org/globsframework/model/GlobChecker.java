package org.globsframework.model;

import org.globsframework.metamodel.DummyModel;
import org.globsframework.metamodel.GlobModel;
import org.globsframework.metamodel.GlobType;
import org.globsframework.xml.tests.XmlComparisonMode;

import java.util.List;

public class GlobChecker {
    private GlobModel model;

    public GlobChecker() {
        this.model = DummyModel.get();
    }

    public GlobChecker(GlobModel model) {
        this.model = model;
    }

    public GlobRepository parse(String xml) {
        GlobRepository globRepository = GlobRepositoryBuilder.createEmpty();
        parse(globRepository, xml);
        return globRepository;
    }

    public void parse(GlobRepository repository, String xml) {
        GlobTestUtils.parse(model, repository, xml);
    }

    public List<Glob> loadGlobs(String xmlInput, GlobType type) {
        GlobRepository tempGlobRepository = GlobRepositoryBuilder.createEmpty();
        parse(tempGlobRepository, xmlInput);
        return tempGlobRepository.getAll(type);
    }

    public void assertEquals(GlobRepository repository, String xml) {
        assertEquals(repository, xml, XmlComparisonMode.EXPECTED_ATTRIBUTES_ONLY);
    }

    public void assertEquals(GlobRepository repository, String xml, XmlComparisonMode comparisonMode) {
        GlobTestUtils.assertEquals(repository, xml, comparisonMode);
    }

    public void assertEquals(GlobRepository repository, GlobType type, String xml) {
        GlobTestUtils.assertEquals(repository, type, xml);
    }

    public GlobRepository getEmptyRepository() {
        return GlobRepositoryBuilder.createEmpty();
    }

    public void assertChangesEqual(ChangeSet changeSet, String expectedXml) {
        GlobTestUtils.assertChangesEqual(changeSet, expectedXml);
    }

    public GlobModel getModel() {
        return model;
    }
}
