package cn.edu.buaa.crypto.access.lsss;

import java.util.HashMap;
import java.util.Map;

import Jama.Matrix;
import cn.edu.buaa.crypto.access.AccessControlEngine;
import cn.edu.buaa.crypto.access.AccessControlParameter;
import cn.edu.buaa.crypto.access.UnsatisfiedAccessControlException;
import cn.edu.buaa.crypto.utils.PairingUtils;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

/**
 * Created by Weiran Liu on 2016/7/21.
 * <p>
 * LSSSPolicyEngine class that implements AccessControlEngine.
 * Since the implementations of function secretSharing, reconstructOmegas are the same in LSSS realization,
 * I create this abstract engine to cover all the same codes.
 */
public abstract class LSSSPolicyEngine implements AccessControlEngine {
    public Map<String, Element> secretSharing(Pairing pairing, Element secret, AccessControlParameter accessControlParameter) {
        if (!(accessControlParameter instanceof LSSSPolicyParameter)) {
            PairingUtils.NotVerifyCipherParameterInstance(this.getEngineName(), accessControlParameter, LSSSPolicyParameter.class.getName());
        }
        LSSSPolicyParameter lsssPolicyParameter = (LSSSPolicyParameter) accessControlParameter;
        int row = lsssPolicyParameter.getRow();
        int column = lsssPolicyParameter.getColumn();
        int[][] lsssMatrix = lsssPolicyParameter.getLSSSMatrix();
        Element[][] elementLSSSMatrix = new Element[row][column];
        for (int i = 0; i < lsssPolicyParameter.getRow(); i++) {
            for (int j = 0; j < lsssPolicyParameter.getColumn(); j++) {
                elementLSSSMatrix[i][j] = pairing.getZr().newElement(lsssMatrix[i][j]).getImmutable();
            }
        }
        //init vector v
        Element[] elementsV = new Element[column];
        elementsV[0] = secret.duplicate().getImmutable();
        for (int i = 1; i < elementsV.length; i++) {
            elementsV[i] = pairing.getZr().newRandomElement().getImmutable();
        }
        //secret share by matrix multiplication
        Map<String, Element> lambdaElementsMap = new HashMap<String, Element>();
        for (int i = 0; i < row; i++) {
            Element elementsLambda = pairing.getZr().newZeroElement().getImmutable();
            for (int j = 0; j < column; j++) {
                elementsLambda = elementsLambda.add(elementLSSSMatrix[i][j].mulZn(elementsV[j])).getImmutable();
            }
            lambdaElementsMap.put(lsssPolicyParameter.getRhos()[i], elementsLambda);
        }
        return lambdaElementsMap;
    }

    public Map<String, Element> reconstructOmegas(Pairing pairing, String[] attributes, AccessControlParameter accessControlParameter) throws UnsatisfiedAccessControlException {
        if (!(accessControlParameter instanceof LSSSPolicyParameter)) {
            PairingUtils.NotVerifyCipherParameterInstance(this.getEngineName(), accessControlParameter, LSSSPolicyParameter.class.getName());
        }
        LSSSPolicyParameter lsssPolicyParameter = (LSSSPolicyParameter) accessControlParameter;
        int[] result;
        String[] minSatisfiedAttributes = lsssPolicyParameter.minSatisfiedAttributeSet(attributes);
        String[] leafAttributes = lsssPolicyParameter.getRhos();
        int[] rows = new int[minSatisfiedAttributes.length];
        int counter = 0;
        for (int i = 0; i < leafAttributes.length; i++) {
            for (String minSatisfiedAttribute : minSatisfiedAttributes) {
                if (leafAttributes[i].equals(minSatisfiedAttribute)) {
                    //??????L??????????????????S??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????result???????????????????????????counter
                    rows[counter++] = i;
                }
            }
        }
        result = new int[counter];
        System.arraycopy(rows, 0, result, 0, counter);
        //filter M to rows from all zero cols and transpose it
        //eliminate all zero cols
        counter = 0;
        int[] cols = new int[result.length];
        for (int j = 0; j < lsssPolicyParameter.getColumn(); j++) {
            for (int aResult : result) {
                if (lsssPolicyParameter.getLSSSMatrix()[aResult][j] != 0) {
                    if (counter == cols.length) {
                        //????????????????????????????????????
                        throw new UnsatisfiedAccessControlException("Invalid access structure or attributes. Unable to reconstruct coefficients.");
                    }
                    //????????????0??????????????????????????????j???????????????cols???????????????,??????counter?????????????????????????????????M???????????????
                    cols[counter++] = j;
                    break;
                }
            }
        }
        double[][] Mreduced = new double[counter][counter];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                //?????????M??????????????????attributes????????????????????????0????????????????????????????????????????????????????????????Mreduced?????????????????????????????????result.length?????????
                Mreduced[j][i] = lsssPolicyParameter.getLSSSMatrix()[result[j]][cols[i]];
            }
        }
//        solve the linear system
        Matrix mA = new Matrix(Mreduced);
        mA = mA.inverse();
        double[] _b = get_identity_vector(mA.getColumnDimension());
        Matrix mb = new Matrix(_b, 1);
        Matrix res = mb.times(mA);
        double[] solution = res.getRowPackedCopy();

        Element[] minSatisfiedOmegaElements = new Element[solution.length];
        for (int i = 0; i < minSatisfiedOmegaElements.length; i++) {
            minSatisfiedOmegaElements[i] = pairing.getZr().newElement((int) solution[i]).getImmutable();
        }

        Map<String, Element> omegaElementsMap = new HashMap<String, Element>();
        for (int i = 0; i < rows.length; i++) {
            for (String attribute : attributes) {
                if (leafAttributes[rows[i]].equals(attribute)) {
                    omegaElementsMap.put(attribute, minSatisfiedOmegaElements[i].duplicate().getImmutable());
                }
            }
        }
        for (String attribute : attributes) {
            if (!omegaElementsMap.containsKey(attribute)) {
                omegaElementsMap.put(attribute, pairing.getZr().newZeroElement().getImmutable());
            }
        }
        return omegaElementsMap;
    }

    private double[] get_identity_vector(int length) {
        //?????????????????????????????????????????????????????????????????????????????????????????????1??????????????????0
        double[] result = new double[length];
        result[0] = 1.0;
        for (int i = 1; i < length; i++) {
            result[i] = 0.0;
        }
        return result;
    }
}
