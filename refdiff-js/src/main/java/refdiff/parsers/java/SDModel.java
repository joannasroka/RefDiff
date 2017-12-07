package refdiff.parsers.java;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import refdiff.core.rast.Location;
import refdiff.core.rast.RastNode;
import refdiff.core.rast.RastNodeRelationship;
import refdiff.core.rast.RastNodeRelationshipType;
import refdiff.core.rast.RastRoot;

public class SDModel {

	private int nodeCounter = 0;
	RastRoot root = new RastRoot();
	Map<String, RastNode> keyMap = new HashMap<>();
	
	public Optional<RastNode> findByKey(String referencedKey) {
		return Optional.ofNullable(keyMap.get(referencedKey));
	}

	public void addReference(RastNode caller, RastNode calleeNode) {
		root.getRelationships().add(new RastNodeRelationship(RastNodeRelationshipType.USE, caller.getId(), calleeNode.getId()));
	}

	public void addSubtype(RastNode supertype, RastNode type) {
		root.getRelationships().add(new RastNodeRelationship(RastNodeRelationshipType.SUBTYPE, type.getId(), supertype.getId()));
	}

	public RastNode createCompilationUnit(String packageName, String sourceFolder, String sourceFilePath, CompilationUnit compilationUnit) {
		RastNode rastNode = new RastNode(++nodeCounter);
		rastNode.setType("CompilationUnit");
		rastNode.setLocation(new Location(sourceFilePath, 0, compilationUnit.getLength()));
		rastNode.setLocalName(sourceFilePath);
		root.getNodes().add(rastNode);
		return rastNode;
	}

	public RastNode createAnonymousType(RastNode parent, String sourceFilePath, String name, ASTNode ast) {
		RastNode rastNode = new RastNode(++nodeCounter);
		rastNode.setType(ast.getClass().getSimpleName());
		rastNode.setLocation(new Location(sourceFilePath, ast.getStartPosition(), ast.getStartPosition() + ast.getLength()));
		rastNode.setLocalName(name);
		parent.getNodes().add(rastNode);
		return rastNode;
	}

	public RastNode createType(String typeName, RastNode parent, String sourceFilePath, AbstractTypeDeclaration ast) {
		RastNode rastNode = new RastNode(++nodeCounter);
		rastNode.setType(ast.getClass().getSimpleName());
		rastNode.setLocation(new Location(sourceFilePath, ast.getStartPosition(), ast.getStartPosition() + ast.getLength()));
		rastNode.setLocalName(typeName);
		parent.getNodes().add(rastNode);
		return rastNode;
	}

	public RastNode createMethod(String methodSignature, RastNode parent, String sourceFilePath, boolean constructor, MethodDeclaration ast) {
		RastNode rastNode = new RastNode(++nodeCounter);
		rastNode.setType(ast.getClass().getSimpleName());
		rastNode.setLocation(new Location(sourceFilePath, ast.getStartPosition(), ast.getStartPosition() + ast.getLength()));
		rastNode.setLocalName(methodSignature);
		parent.getNodes().add(rastNode);
		return rastNode;
	}

	public void setReturnType(RastNode method, String normalizeTypeName) {
		// TODO Auto-generated method stub
		
	}

	public void addParameter(RastNode method, String identifier, String typeName) {
		// TODO Auto-generated method stub
		
	}
	
}
