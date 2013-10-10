/**
 * Copyright (c) 2013 Bernard Leach
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.leachbj.hsmsim.commands

import org.scalatest.FunSuite
import scala.collection.immutable.Map
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.leachbj.hsmsim.util.HexConverter

@RunWith(classOf[JUnitRunner])
class ImportDesKeySuite extends FunSuite {
  test("import des key with valid parity should match test vector") {
    // decrypted key 022F04000000D58EA28B2F4DCD74375D665834B4970F6B7539BB2CC922EE8C866BCC1AA132D6A43B46AD961BC5EC6017B6D48BAEEE01D35257D589D3E7F805C63344ECE9EEB30000E53325C414207C4D9CD3E63826F6C45330129938828EADF214EF32001C0BE61E4C261D0A84F28497A419B778964D923D8F9423BF68E10B9D8CF3F3C22DBB247900008E5F170774DE88F824E8EEE5787864B4F24E267CC8861749B30447DD671621E46D7CD9C90EBD2E9D9565248DB2749EABE236E539068D455003D97783489BF477000098CCC3D80D6AFD891337EED019F9D8377561BB7B01B473F6B89F76AABD5D441432C4135C58A1ADBA6D667A506433B6D3B50D6D2A45EB5D13B34D4D2C1E7CC2FB000081B9CC50502840BE6D95BE52266DCD50A482C0467426DD3634BFC5E7C68DFFC8A02EF398DF500C42417DC8FFEC08391BC4DA76ED20E0D4AF0D8DE0FAD441D3367BCDA33A000000000000
    val rsa = HexConverter.fromHex("1837180A8E0A914C6222FFD98444C5447C3B8EE34CB7F3B6F1B487AFAC6566F60EE117EA97EA1BD7119467405EB969AFEF478F73059582BC9D6FBE232424EBA13D17B9E059FCEF0B1FE13FA53C24810B026EF779067EBA38533192DE9E3C3A5099B3C909129D54DF1A8976C24AC69E89AC0F50F527D01C1EEAC1A1056D89387AB9E30EF9087079D59FC315C70392B43F276306AD9D895349258E9B79A03DF07AC1D37AD4533339175A15FFB7D91F83CB755F14013EBA58B3A699959EF4B1944F4C441FC4EFF7B4DBBF3B49B7F8CC175641659254877D62C124AEB57A4B1E03D11C5081DAF0A24063E035E10FA2D3EC018A8B6061A6ABABE39BB963622AE7E2602E396C0D9F5E2041E118EE5609225BF466703A389150D41CC527A2D6A9A820914260895A01B5669E9076914F6990AEB659858602149C4BBC2CFEE2352F0D56CE3E6B071CEA7CCEE6B3C1B5B90DE9EEE36A4494BD80CB125C").toArray
    val des = HexConverter.fromHex("A2A7273D4B1F0F514FB716DF297909E92558DCB20AB44083761B1815425BD4A562EA06E913CE251324C04496B95EFDC2771F116ED602D198A5C5EDC725D7D115B4087F4DC0D299DAC6484892FB0493F6B1B8AC96FCC5F36D5FFB5054E1CE6ED8D8B6990786DE9F7AEBC0AA921EB9004C6F9BADA4715F7680433EF10B89516635").toArray
    val r = ImportDesKeyRequest(des, rsa, 'T')

    val resp = ImportDesKeyResponse.createResponse(r).asInstanceOf[ImportDesKeyResponse]
    val importDesKeyResponse = resp.asInstanceOf[ImportDesKeyResponse]
    assert(importDesKeyResponse.desKey === HexConverter.fromHex("CF7471F997A26A9A82B16A616434312FD101DF4F6C426E97").toArray)
    assert(importDesKeyResponse.keyCheckValue === HexConverter.fromHex("D58C6BB34AA6").toArray)
  }

  test("import des key with invalid parity should correct parity") {
    // decrypted key 022F04000000C2889EAB54871401255AF447609AF33B39DDA4F49650C991F44FE9DB6E6AEA52FFDB117FEA931F34E92EA2DB7E3D82821516472500A4A99BEE030A89E7C12C2B0000C1EBEEE3606B8255697916CD1989C3C8BB100308D46C2FD12D4D67D269361B68E69905DEA0865826A377D4AA4F2BCA7DA87EFC7DC4785CBB29A7014CD774E9C5000081B069C78DAF62AB6E3CA2DA40674CD22693C34DB98B310BF8354692499C9C37553CB655470CBF789B746C92542901AC0E0EDA18AB1871129EACB1B1452B72C700008147F497959D018E4650B9DE11068285D20AACB08D9D75361E339A8C46241245EF10AE946B043AC46CFA8DC6DF7286FE7054A853D8503DD21BC4AB888FA346830000AE99EF8DA8724FD45EE6D8631D2D34E90A61C3D4678A9C1363C5841BCD685B4A82AE052A28DDD6F19369CB7ABE5EF9B06C8D94850E749DF995C45871343595B9EA5FD819000000000000
    val rsa = HexConverter.fromHex("6780AD7C9D92081B578566F14361C60450A603A63FB8F1F67B46DC15A9C785F17B10BDD6A9A95552804F5C41D7F24D13A16F89716511E44CAA70A3E9D4B1026A2538E46BE466AA2BF5154D04D1AF924D89010DBF6390ED13A97C18FFF4C643984EE3650FD14DFF3DD0AECFF1D67906FDFFA7256792C82E512471785E4859CE48A06D84DCE20AD0B55517F9044470CF224E78E416CE894140C9D2DDD372164BE9068733081B051F559BAE2D8A782F42590AF825122DDB494B63065BE67A1E80817CBF47CEF94D7AE239A482449636D7E3E9A2C3D3975FEF747E3816895D9935DBBE224BD07A035D2D6869B9135D964756081D09D4C554E84D5216241B76DF6A2169528600CCB3CA2DA323C282CF70CB030A46568B6FB700783B9EF3BA63AA32A53233D454E0DDC914DFE2C4BE411F307E33F394735AD8842444547265C1F9FFF70ABC8EA33D0B6B6C923D1B868380481317C250514838285D").toArray
    val des = HexConverter.fromHex("0D6A3202E0D1E02AED8FC0440F89E021BEA9AE7441B2B0EF0682C65DFCBDD9544FC384CA1101252BBE40A6C9DDA293237F217C62E9B879CA32D92C9D7A81F9160934B1A66A0399CE56E403D61F00D3E7F9E549BD29509965AF54790769821570EE00A3EEF9B6A6BBE247EA535E35150CCF6DDC082800D86EA4DD9413419EB2DB").toArray
    val r = ImportDesKeyRequest(des, rsa, 'T')

    val resp = ImportDesKeyResponse.createResponse(r).asInstanceOf[ImportDesKeyResponse]
    val importDesKeyResponse = resp.asInstanceOf[ImportDesKeyResponse]
    assert(importDesKeyResponse.desKey === HexConverter.fromHex("971803BA969012152827CC97A98D18D50BE6559CDFF6F7E1").toArray)
    assert(importDesKeyResponse.keyCheckValue === HexConverter.fromHex("6EC2A9AE788F").toArray)
  }

  test("import 512bit key management key test vector") {
    // decrypted key 0130020000DF456ECF91719C350B17F8953265691494DF018FD441AF3FF279A20BF784313B00C11DFBEA6CA09F5879A5D1357B737E4BA0CAC43037AE682655EC28C374A2DED70094D8F48A60F668235CBAA5B8CC439B630DEA010A8D811F7FF6FBC15D4FAD76270080BEA7F19DC06A3AFBC3E0CE524CFEDD15DC82CACFC99AC439481B2CF86C948F006D6BF174293F21F914A306A0813CCDAF29D99A5C2B691E8FEEFC1AC620D9A688DB8AE837000000
    val rsa = HexConverter.fromHex("E14A3B918403718231C100F4882350A2289923C8FF3CE624152C5DE08002734AD28ACC2E01F72C745CB979C4A1FDD89B39FFA492B16C6B7C406439BA1DFDDA90C59B7C93239BC0B1709FE97E0871A1AE7712E052AC33FFD52C1B566125179276D378BEF382965A40C4C7AA20DFCDEE317FAD767A1AB3F64561AADE44663B009DF84FBD0311812A9EC08EB444B3401CD985BDB5367525D57565240BC4D5CD4EF9B7FCEDB08DB14B10DA5D0A6A82F8FF94").toArray
    val des = HexConverter.fromHex("5DE7AB4B9AB6066C9E855E25C9783B33BF2F5B21EB0E432189B98472C9CCDD8E299A41AAE7E123663EC2D1B556964B5F15E690723F974118FAE3EDD7A1BBD1E9").toArray
    // 7BF4D40FE6587A76
    val r = ImportDesKeyRequest(des, rsa, 'T')

    val resp = ImportDesKeyResponse.createResponse(r).asInstanceOf[ImportDesKeyResponse]
    val importDesKeyResponse = resp.asInstanceOf[ImportDesKeyResponse]
    assert(importDesKeyResponse.desKey === HexConverter.fromHex("F9856E85A3A471BB3D21D07610129AAEA8499F4793B75256").toArray)
    assert(importDesKeyResponse.keyCheckValue === HexConverter.fromHex("FF326A093235").toArray)
  }

  test("import 512bit public exponent 0x10001 signature only key test vector") {
    // decrypted key 0030020000FAD927E2EDE0DC43CF4FA5647AC537B83FC468B423D1B48C375A9D40AF2FD7C300E134B907BA48B37ADC6EAE42F7F321D070B25063B3356610F7C006E8D6480E3D009A12592253D341D6D0756DF39A4899E1AC20F9E27301B1B051AB715CB27D633B0099DAB0121474F8911629534A1324FA53F1BA106B9C91FD91D8004B78247483AD00DBB5A4E44CE3417E1F9DDF4987749B0CF0F5151CE7230AD2D7D972166CB5CD54FB4E373C000000
    val rsa = HexConverter.fromHex("60779D7300F8C209B4C443C67367ADECBD80CAE5972843B9BE2990B573903DDE765CD87AA823056AC472674C5AF9A75127FC7E66340E3C2CC27F051406E6803D5556991FFE66A9C3B1288E7FC893C3B3123C955ED8DBD8E9530F1D5FB4CD3E1050BA913F0430C230F596306BEFDB096ABC8B12B2111A5CBF951F879E0AB063368BAA2BDD1BA69E80D285323BFD267B48720B81B33F0B5A23EB5552DE94F3319F953FB508A27CA894FC18EFE7D068E53D").toArray
    val des = HexConverter.fromHex("3D22133C89295D71C14228C957BC669EAD027A7F910273DF04738078FE6F61E217CB93C63E07F7E538968C061F39B87A23F112617BA57A2E047C0FA263855735").toArray
    // 7BF4D40FE6587A76
    val r = ImportDesKeyRequest(des, rsa, 'T')

    val resp = ImportDesKeyResponse.createResponse(r).asInstanceOf[ImportDesKeyResponse]
    val importDesKeyResponse = resp.asInstanceOf[ImportDesKeyResponse]
    assert(importDesKeyResponse.desKey === HexConverter.fromHex("2FB4F1DCBCA6165A85D78B7A520DC70B6F251EA82FB48380").toArray)
    assert(importDesKeyResponse.keyCheckValue === HexConverter.fromHex("4E11B5A42945").toArray)

    // 0130020000DF456ECF91719C350B17F8953265691494DF018FD441AF3FF279A20BF784313B00C11DFBEA6CA09F5879A5D1357B737E4BA0CAC43037AE682655EC28C374A2DED70094D8F48A60F668235CBAA5B8CC439B630DEA010A8D811F7FF6FBC15D4FAD76270080BEA7F19DC06A3AFBC3E0CE524CFEDD15DC82CACFC99AC439481B2CF86C948F006D6BF174293F21F914A306A0813CCDAF29D99A5C2B691E8FEEFC1AC620D9A688DB8AE837000000
    // 0030020000FAD927E2EDE0DC43CF4FA5647AC537B83FC468B423D1B48C375A9D40AF2FD7C300E134B907BA48B37ADC6EAE42F7F321D070B25063B3356610F7C006E8D6480E3D009A12592253D341D6D0756DF39A4899E1AC20F9E27301B1B051AB715CB27D633B0099DAB0121474F8911629534A1324FA53F1BA106B9C91FD91D8004B78247483AD00DBB5A4E44CE3417E1F9DDF4987749B0CF0F5151CE7230AD2D7D972166CB5CD54FB4E373C000000
  }

}