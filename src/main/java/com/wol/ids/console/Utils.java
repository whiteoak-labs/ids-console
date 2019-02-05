package com.wol.ids.console;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

public class Utils
{
	public static final String DEFAULT_DATETIME_FORMAT = "yyyyMMdd'T'HH:mm'Z'";

	/**
	 * Utility class providing helper methods to perform encryption and decryption
	 * tasks.
	 * 
	 * @author tcook
	 *
	 */
	public static class Crypto
	{
		public static final int DEFAULT_KEYSIZE = 2048;

		private static final String DEFAULT_CIPHER = "AES/CBC/PKCS5PADDING";
		private static final String DEFAULT_KEY = "Q/GX0?9E/GZoJ/wEnGuEZ$;M}q`/k^t&";
		private static final String DEFAULT_IV_SPEC = "db20e0d7fb2a4beg";

		/**
		 * Encrypts the supplied String value using AES/CBC/PKCS5PADDING
		 * 
		 * @param value
		 * @see Cipher
		 * @return Base64 encoded String containing the encrypted result of the input
		 *         value
		 */
		public static String encrypt(String value)
		{
			String result = null;

			try
			{
				IvParameterSpec initVector = new IvParameterSpec(DEFAULT_IV_SPEC.getBytes());
				SecretKeySpec keySpec = new SecretKeySpec(DEFAULT_KEY.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER);
				cipher.init(Cipher.ENCRYPT_MODE, keySpec, initVector);
				byte[] encrypted = cipher.doFinal(value.getBytes());
				result = new String(Base64.getEncoder().encode(encrypted));
			}
			catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
			        | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e)
			{
				System.out.println("Failed to encrypt value: " + e.getMessage());
				e.printStackTrace();
			}

			return result;
		}

		/**
		 * Decrypts the supplied value using AES/CBC/PKCS5PADDING
		 * 
		 * @param value
		 * @return Original value which was previously encrypted.
		 */
		public static String decrypt(String value)
		{
			String result = null;

			try
			{
				IvParameterSpec initVector = new IvParameterSpec(DEFAULT_IV_SPEC.getBytes());
				SecretKeySpec keySpec = new SecretKeySpec(DEFAULT_KEY.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER);
				cipher.init(Cipher.DECRYPT_MODE, keySpec, initVector);
				byte[] decoded = Base64.getDecoder().decode(value.getBytes());
				byte[] decrypted = cipher.doFinal(decoded);
				result = new String(decrypted);
			}
			catch (IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException
			        | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e)
			{
				System.out.println("Failed to decrypt value: " + e.getMessage());
				e.printStackTrace();
			}

			return result;
		}
	}

	/**
	 * Attempts to format given date using the supplied date format.
	 * 
	 * @param date
	 *            Date object to format if nothing is supplied the current date is
	 *            used
	 * @param format
	 *            String conforming to SimpleDateFormat specifications, if not
	 *            supplied an ISO8601 compliant format is used.
	 * 
	 * @return Formatted date value.
	 */
	public static String formatDate(Date date, String format)
	{
		date = (date == null || !(date instanceof Date)) ? new Date() : date;
		TimeZone timeZone = StringUtils.isBlank(format) ? TimeZone.getTimeZone("UTC")
		        : TimeZone.getDefault();
		format = StringUtils.isBlank(format) ? DEFAULT_DATETIME_FORMAT : format;

		SimpleDateFormat formatter = new SimpleDateFormat(format);
		formatter.setTimeZone(timeZone);
		return formatter.format(date);
	}

	/**
	 * Adjusts supplied date by adding/subtracting the specified number of minutes.
	 * 
	 * @param dateToAdjust
	 * @param adjustByMins
	 * @return Date which has been adjusted by specified number of minutes
	 */
	public static Date adjustDateByMinutes(Date dateToAdjust, int adjustByMins)
	{
		dateToAdjust = (dateToAdjust == null || !(dateToAdjust instanceof Date)) ? new Date()
		        : dateToAdjust;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToAdjust);
		calendar.add(Calendar.MINUTE, adjustByMins);
		return calendar.getTime();
	}

}
