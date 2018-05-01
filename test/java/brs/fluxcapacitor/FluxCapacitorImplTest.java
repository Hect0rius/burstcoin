package brs.fluxcapacitor;

import static brs.common.AliasNames.DYMAXION_END_BLOCK;
import static brs.common.AliasNames.DYMAXION_START_BLOCK;
import static brs.fluxcapacitor.FeatureToggle.DYMAXION;
import static brs.fluxcapacitor.FeatureToggle.POC2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import brs.Alias;
import brs.Blockchain;
import brs.common.Props;
import brs.services.AliasService;
import brs.services.PropertyService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FluxCapacitorImplTest {

  private Blockchain blockchainMock;
  private AliasService aliasServiceMock;
  private PropertyService propertyServiceMock;

  private FluxCapacitorImpl t;

  @Before
  public void setUp() {
    blockchainMock = mock(Blockchain.class);
    aliasServiceMock = mock(AliasService.class);
    propertyServiceMock = mock(PropertyService.class);

    when(propertyServiceMock.getBoolean(eq(Props.DEV_TESTNET))).thenReturn(false);

    t = new FluxCapacitorImpl(blockchainMock, aliasServiceMock, propertyServiceMock);
  }

  @Test
  public void isActive_hardcodedHeights_withinConstraints() {
    when(blockchainMock.getHeight()).thenReturn(500000);

    assertTrue(t.isActive(POC2));
  }

  @Test
  public void isActive_hardcodedHeights_beforeConstraints() {
    when(blockchainMock.getHeight()).thenReturn(499999);

    assertFalse(t.isActive(POC2));
  }

  @Test
  @Ignore
  public void isActive_hardcodedHeights_afterConstraints() {
    when(blockchainMock.getHeight()).thenReturn(430001);

    assertFalse(t.isActive(POC2));
  }

  /*
  @Test
  public void isActive_hardcodedHeights_propertyOverriding_shouldNotWorkWhenNotTestNet() {
    when(blockchainMock.getHeight()).thenReturn(49999);

    when(propertyServiceMock.getInt(eq(Props.DEV_FEATURE_POC2_END), eq(-1))).thenReturn(50000);
    when(propertyServiceMock.getInt(isNull(), eq(-1))).thenReturn(-1);

    assertFalse(t.isActive(FEATURE_FOUR));
  }

  @Test
  public void isActive_hardcodedHeights_propertyOverriding_shouldWorkWhenTestNet() {
    when(blockchainMock.getHeight()).thenReturn(49999);

    when(propertyServiceMock.getInt(eq(Props.DEV_FEATURE_POC2_END), eq(-1))).thenReturn(50000);
    when(propertyServiceMock.getInt(isNull(), eq(-1))).thenReturn(-1);
    when(propertyServiceMock.getBoolean(eq(Props.DEV_TESTNET))).thenReturn(true);

    assertTrue(t.isActive(FEATURE_FOUR));
  }
  */

  @Test
  public void isActive_hardcodedHeights_forTestNet() {
    when(propertyServiceMock.getBoolean(eq(Props.DEV_TESTNET))).thenReturn(true);
    when(propertyServiceMock.getInt(isNull(), anyInt())).thenReturn(-1);

    when(blockchainMock.getHeight()).thenReturn(88999);

    assertTrue(t.isActive(POC2));

    when(blockchainMock.getHeight()).thenReturn(30000);

    assertFalse(t.isActive(POC2));
  }

  @Test
  @Ignore
  public void isActive_aliasBoundHeights_withinConstraints() {
    when(blockchainMock.getHeight()).thenReturn(5000);

    final Alias startAlias = mock(Alias.class);
    when(startAlias.getAliasURI()).thenReturn("1000");

    final Alias endAlias = mock(Alias.class);
    when(endAlias.getAliasURI()).thenReturn("9999");

    when(aliasServiceMock.getAlias(eq(DYMAXION_START_BLOCK))).thenReturn(startAlias);
    when(aliasServiceMock.getAlias(eq(DYMAXION_END_BLOCK))).thenReturn(endAlias);

    assertTrue(t.isActive(DYMAXION));
  }

  @Test
  public void getInt_defaultValue() {
    when(blockchainMock.getHeight()).thenReturn(88000);

    assertEquals((Integer) 255, t.getInt(FluxInt.MAX_NUMBER_TRANSACTIONS));
  }

  @Test
  public void getInt_firstHistoricalValue() {
    when(blockchainMock.getHeight()).thenReturn(500000);

    assertEquals((Integer) 1020, t.getInt(FluxInt.MAX_NUMBER_TRANSACTIONS));
  }

  @Test
  public void getInt_defaultValue_testNet() {
    when(propertyServiceMock.getBoolean(eq(Props.DEV_TESTNET))).thenReturn(true);

    when(blockchainMock.getHeight()).thenReturn(5);

    assertEquals((Integer) 255, t.getInt(FluxInt.MAX_NUMBER_TRANSACTIONS));
  }

  @Test
  public void getInt_firstHistoricalValue_testNet() {
    when(propertyServiceMock.getBoolean(eq(Props.DEV_TESTNET))).thenReturn(true);

    when(blockchainMock.getHeight()).thenReturn(88000);

    assertEquals((Integer) 1020, t.getInt(FluxInt.MAX_NUMBER_TRANSACTIONS));
  }

  @Test
  public void getInt_propertyValues_testNet() {
    when(propertyServiceMock.getBoolean(eq(Props.DEV_TESTNET))).thenReturn(true);

    when(propertyServiceMock.getString(eq(Props.DEV_BLOCK_SIZE_SETTING))).thenReturn("50;100:1;200:2000");

    when(blockchainMock.getHeight()).thenReturn(88000);

    assertEquals((Integer) 50, t.getInt(FluxInt.MAX_NUMBER_TRANSACTIONS, 1));
    assertEquals((Integer) 1, t.getInt(FluxInt.MAX_NUMBER_TRANSACTIONS, 100));
    assertEquals((Integer) 2000, t.getInt(FluxInt.MAX_NUMBER_TRANSACTIONS, 202));
  }
}
